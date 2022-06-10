package com.server.hammer.Config;

import com.server.hammer.ApplicationContextRegister;
import com.server.hammer.Entity.UserInMeeting;
import com.server.hammer.Service.MeetingsService;
import com.server.hammer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
@Component
@Slf4j
@Service
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    //用于记录会议室中的人
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    //对应每个session与其studentId
    private static final Map<Session, String> match=new ConcurrentHashMap<>();
    private static final Map<String,Session> reverseMatch=new ConcurrentHashMap<>();
    //辨别是发送会议号还是聊天发送消息
    private boolean isFirst=true;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";
    private String roomName="";
    //记载当前自动邀请入会的人员
    private List<UserInMeeting> users=new ArrayList<>();

    boolean flag=true;
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingsService meetingsService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) throws IOException {
        this.session = session;
        webSocketSet.add(this); // 加入set中
        this.sid = sid;
        addOnlineCount();// 在线数加1

        match.put(session,sid);
        reverseMatch.put(sid,session);
        isFirst=true;//将第一次发送信息记为真

        //log.info(getWeekday(date).toString());
        try {
            sendMessage("conn_success");
            log.info("有新客户端开始监听,sid=" + sid + ",当前在线人数为:" + getOnlineCount());
            //AutoMeeting("12");
            ApplicationContext act = ApplicationContextRegister.getApplicationContext();
            userService=act.getBean(UserService.class);

            users = userService.getUsers("12");
            if (users==null)
                 flag=false;
            if(flag)
            log.info("有这些用户"+users.toString());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();              // 在线数减1
        // 断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid=" + sid + "的客户端");
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session,boolean flag,@PathParam("sid")String sid) {
        flag=isFirst;
        if(flag){
            if (!rooms.containsKey(message)) {
                // 创建房间不存在时，创建房间
                Set<Session> room = new HashSet<>();
                // 添加用户
                room.add(session);
                rooms.put(message, room);
            } else {
                // 房间已存在，直接添加用户到相应的房间
                rooms.get(message).add(session);
            }
            System.out.println("a client has connected!");
            isFirst=false;
            roomName =message;

        }
        this.sid=sid;
        log.info("收到来自客户端 sid=" + sid + " 的信息:" + message);
        // 群发消息
        HashSet<String> sids = new HashSet<>();
        for (Session sessions : rooms.get(roomName)) {
            sids.add(match.get(sessions));
        }
        log.info(sids.toString());
        try {
            sendMessage("会议号 "+roomName+"中用户" + this.sid + "发布消息：" + message, sids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static void sendMessage(String message, HashSet<String> toSids) throws IOException {
        log.info("推送消息到客户端 " + toSids + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给传入的sid，为null则全部推送
                if (toSids.size() <= 0) {
                    item.sendMessage(message);
                } else if (toSids.contains(item.sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    public  void AutoMeeting(String roomId) throws IOException {
        ApplicationContext act = ApplicationContextRegister.getApplicationContext();
        userService=act.getBean(UserService.class);
        users = userService.getUsers(roomId);
        isFirst=false;
        //this.roomName=roomId;
        if (users == null) log.info("没查到");
        else {
            for (UserInMeeting user : users) {
                if (reverseMatch.containsKey(user.getUserId())) {
                    if (!rooms.containsKey(roomId)) {
                        // 创建房间不存在时，创建房间
                        Set<Session> room = new HashSet<>();
                        // 添加用户
                        room.add(session);
                        rooms.put(roomId, room);
                    } else {
                        // 房间已存在，直接添加用户到相应的房间
                        rooms.get(roomId).add(session);
                    }
                    HashSet<String> sids = new HashSet<>();
                    for (Session sessions : rooms.get(roomId)) {
                        sids.add(match.get(sessions));
                    }
                    sendMessage("会议号 " + roomId + "中用户" + "已经入", sids);
                }
            }
        }
    }





}
