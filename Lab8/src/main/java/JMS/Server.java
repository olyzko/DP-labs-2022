package JMS;

import com.ibm.mq.*;

public class Server {
    private MQQueue Q1 = null;
    private MQQueue Q2 = null;

    public void start(String QMName, String IP, int port, String channel,
                      String Q1_name, String Q2_name)
            throws MQException {

        MQEnvironment.hostname = IP;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;

        MQQueueManager QM = new MQQueueManager(QMName);
        Q1 = QM.accessQueue(Q1_name, MQC.MQOO_INPUT_EXCLUSIVE);
        Q2 = QM.accessQueue(Q2_name, MQC.MQOO_OUTPUT);
        int i = 0;
        while (processQuery()) i++;
        Q1.close();
        Q2.close();
        QM.disconnect();
        System.out.println(i + "Queries are worked");
    }

    public boolean processQuery() {
        try {
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            gmo.options = MQC.MQGMO_WAIT;
            gmo.waitInterval = 3000;
            MQMessage query = new MQMessage();
            Q1.get(query, gmo);
            int oper = query.readInt();
            int v1 = query.readInt();
            int v2 = query.readInt();
            int result;
            result = (oper == 0) ? (v1 + v2) : (v1 - v2);

            MQMessage response = new MQMessage();
            response.writeInt(oper);
            response.writeInt(v1);
            response.writeInt(v2);
            response.writeInt(result);

            Q2.put(response);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws MQException {
        (new Server()).start("QM1", "localhost", 1414,
                "SYSTEM.DEF.SVRCONN", "SRV.Q", "CL.Q");
    }
}