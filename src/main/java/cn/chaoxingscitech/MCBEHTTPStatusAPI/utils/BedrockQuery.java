package cn.chaoxingscitech.MCBEHTTPStatusAPI.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class BedrockQuery {
    private static final byte ID_UNCONNECTED_PING = 0x01;
    private static final byte[] UNCONNECTED_MESSAGE_SEQUENCE = {(byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};
    private static long dialerID = new Random().nextLong();

    private boolean online;
    private String motd;

    private int protocolVersion;
    private String minecraftVersion;
    private int playerCount;
    private int maxPlayers;
    private String software;
    private String gamemode;

    public BedrockQuery(boolean online, String motd, int protocolVersion, String minecraftVersion, int playerCount, int maxPlayers, String software, String gamemode) {
        this.online = online;
        this.motd = motd;
        this.protocolVersion = protocolVersion;
        this.minecraftVersion = minecraftVersion;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
        this.software = software;
        this.gamemode = gamemode;
    }

    public static BedrockQuery create(String serverAddress, int port) {
        try {
            InetAddress address = InetAddress.getByName(serverAddress);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeByte(ID_UNCONNECTED_PING);
            dataOutputStream.writeLong(System.currentTimeMillis() / 1000);
            dataOutputStream.write(UNCONNECTED_MESSAGE_SEQUENCE);
            dataOutputStream.writeLong(dialerID++);

            byte[] requestData = outputStream.toByteArray();
            byte[] responseData = new byte[1024 * 1024 * 4];

            DatagramSocket socket = new DatagramSocket();
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address, port);
            socket.send(requestPacket);

            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
            socket.setSoTimeout(2000);
            socket.receive(responsePacket);

            String[] splittedData = new String(responsePacket.getData(), 35, responsePacket.getLength()).split(";");

            int protocol = Integer.parseInt(splittedData[2]);
            int playerCount = Integer.parseInt(splittedData[4]);
            int maxPlayers = Integer.parseInt(splittedData[5]);

            return new BedrockQuery(true, splittedData[1], protocol, splittedData[3], playerCount, maxPlayers, splittedData[7], splittedData[8]);
        } catch (Exception e) {
            return new BedrockQuery(false, "", -1, "", 0, 0, "", "");
        }
    }

    public static long getDialerID() {
        return dialerID;
    }

    public boolean isOnline() {
        return online;
    }

    public String getMotd() {
        return motd;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getSoftware() {
        return software;
    }

    public String getGamemode() {
        return gamemode;
    }
}