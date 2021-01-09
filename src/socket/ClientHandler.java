package socket;

import database.MongoDBProvider;
import database.Person;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Scanner inStream;
    private PrintWriter outStream;
    private final MongoDBProvider mongoDBProvider;

    public ClientHandler(Socket socket) {
        mongoDBProvider = MongoDBProvider.getInstance();
        try {
            inStream = new Scanner(socket.getInputStream());
            outStream = new PrintWriter(socket.getOutputStream());
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (inStream.hasNext()) {
                String inputMessage = inStream.next();
                System.out.println(inputMessage);
                Request request = new Request(inputMessage);
                Response response = new Response();
                switch (request.getAction()) {
                    case ADD_USER -> {
                        try {
                            mongoDBProvider.insertPersonData(new Person(request.getTagID(), request.getName(), request.getBalance(), request.getBalance()));
                            response.setResponseStatus(Response.Status.OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setResponseStatus(Response.Status.ERROR);
                        }
                        response.setAction(request.getAction());
                        response.setTagID(request.getTagID());
                        sendMessage(response.toString());
                    }
                    case DELETE_USER -> {
                        try {
                            mongoDBProvider.deletePerson(request.getTagID());
                            response.setResponseStatus(Response.Status.OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setResponseStatus(Response.Status.ERROR);
                        }
                        response.setAction(request.getAction());
                        response.setTagID(request.getTagID());
                        sendMessage(response.toString());
                    }
                    case EDIT_BALANCE -> {
                        try {
                            mongoDBProvider.editBalanceByID(request.getTagID(), request.getBalance());
                            response.setResponseStatus(Response.Status.OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setResponseStatus(Response.Status.ERROR);
                        }
                        response.setAction(request.getAction());
                        response.setTagID(request.getTagID());
                        sendMessage(response.toString());
                    }
                    case EDIT_NAME -> {
                        try {
                            mongoDBProvider.editNameByID(request.getTagID(), request.getName());
                            response.setResponseStatus(Response.Status.OK);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setResponseStatus(Response.Status.ERROR);
                        }
                        response.setAction(request.getAction());
                        response.setTagID(request.getTagID());
                        sendMessage(response.toString());
                    }
                    case GET_USER -> {
                        Person person = mongoDBProvider.findInDatabaseByID(request.getTagID());
                        response.setAction(request.getAction());
                        response.setTagID(request.getTagID());
                        if (person != null) {
                            response.setResponseStatus(Response.Status.OK);
                            response.setPerson(person);
                        } else {
                            response.setResponseStatus(Response.Status.NOT_FOUND);
                        }
                        sendMessage(response.toString());
                    }
                }
            }
        }
    }

    private void sendMessage(String message) {
        outStream.println(message);
        outStream.flush();
    }
}
