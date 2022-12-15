package JMS;

import com.ibm.mq.*;
import java.io.IOException;
public class Client {
    private MQQueueManager QM;
    private MQQueue Q1;
    private MQQueue Q2;

    public Client(String QMName, String IP, int port, String channel,
                  String Q1_name, String Q2_name) throws MQException {
        MQEnvironment.hostname = IP;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;
        QM = new MQQueueManager(QMName);
        Q1 = QM.accessQueue(Q1_name, MQC.MQOO_OUTPUT);
        Q2 = QM.accessQueue(Q2_name, MQC.MQOO_INPUT_EXCLUSIVE);
    }

    private void sendQuery(int operation, int value1, int value2) throws IOException, MQException {
        MQMessage query = new MQMessage();
        query.writeInt(operation);
        query.writeInt(value1);
        query.writeInt(value2);
        Q1.put(query);
    }

    public void sum(int value1, int value2) throws IOException,
            MQException {
        sendQuery(0, value1, value2);

    }

    public void sub(int value1, int value2) throws IOException,
            MQException {
        sendQuery(1, value1, value2);
    }

    public boolean printResult() {
        try {
            MQMessage response = new MQMessage();
            Q2.get(response);
            int oper = response.readInt();
            int v1 = response.readInt();
            int v2 = response.readInt();
            int res = response.readInt();
            String s = (oper == 0) ? "+" : "-";
            System.out.println(v1 + s + v2 + "=" + res);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void disconnect()
            throws MQException {
        Q1.close();
        Q2.close();
        QM.disconnect();
    }

    public static void main1() {
        try {
            Client client =
                    new Client("QM1", "localhost", 1414,
                            "SYSTEM.DEF.SVRCONN", "SRV.Q", "CL.Q");
            client.sum(15, 20);
            client.sub(30, 38);
            client.sum(100, 200);
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2() throws MQException {
        Client client = new Client("QM1", "localhost", 1414,
                "SYSTEM.DEF.SVRCONN", "SRV.Q", "CL.Q");
        while (client.printResult()) ;
    }
    public static void main(String[] args) throws IOException, MQException
    {
        main1();
    }
}