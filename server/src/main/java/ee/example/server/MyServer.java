package ee.example.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    public static final int PORT = 8899;//端口号
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;

    public static void main(String[] args) {
        new MyServer();
    }

    public MyServer() {
        //服务器创建流程如下
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("local host:"+addr);

            //1.创建ServerSocket
            server = new ServerSocket(PORT);
            //创建线程池
            System.out.println("--服务器开启中--");
            while (true) {
                //2.等待接收请求   这里接收客户端的请求
                Socket client = server.accept();
                System.out.println("得到客户端连接：" + client);
                mList.add(client);
                //初始化完成

                //执行线程
                new Thread(new Service(client)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Service implements Runnable {
        private Socket socket;
        private BufferedReader in = null;
        private String content = "";

        public Service(Socket clientsocket) {
            this.socket = clientsocket;
            try {
                //3.接收请求后创建链接socket
                //4.通过InputStream  outputStream进行通信
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));

                content = "用户:" + this.socket.getInetAddress() + "~加入了游戏"
                        + "当前在线人数:" + mList.size();
                this.sendmsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while ((content = in.readLine()) != null) {
                    for (Socket s : mList) {
                        System.out.println("从客户端接收到的消息为：" + content);
                        if (content.equals("bye")) {
                            System.out.println("~~~~~~~~~~~~~");
                            mList.remove(socket);
                            in.close();
                            content = "用户:" + socket.getInetAddress()
                                    + "退出:" + "当前在线人数:" + mList.size();
                            //5.关闭资源
                            socket.close();
                            this.sendmsg();
                            break;
                        } else {
                            this.sendmsg();
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        //为连接上服务端的每个客户端发送信息
        public void sendmsg() {
            System.out.println(content);
            int num = mList.size();
            for (int index = 0; index < num; index++) {
                Socket mSocket = mList.get(index);
                PrintWriter pout = null;
                try {
                    //PrintWriter 和BufferWriter使用方法相似
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8")), true);
                    pout.println(content);  //将输出流包装为打印流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
