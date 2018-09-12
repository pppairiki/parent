package com.pourtoujours.util;

import com.pourtoujours.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTranscoder implements SerializeTranscoder {

    private ObjectTranscoder() {

    }

    private static ObjectTranscoder obj = new ObjectTranscoder();

    public static ObjectTranscoder getInstance() {
        return obj;
    }

    public byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv=null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if(os!=null)os.close();
                if(bos!=null)bos.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    public Object deserialize(byte[] in) {
        Object rv=null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if(in != null) {
                bis=new ByteArrayInputStream(in);
                is=new ObjectInputStream(bis);
                rv=is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null)is.close();
                if(bis!=null)bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }


    public static void main(String args[]) {
        List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("name", "andy");
        m1.put("age", 20);
        l1.add(m1);
        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("name", "lucy");
        m2.put("age", 30);
        l1.add(m2);

        List<Map<String, Object>> l2 = (List<Map<String, Object>>)ObjectTranscoder.getInstance().deserialize(ObjectTranscoder.getInstance().serialize(l1));
        System.out.println(l2.size());
        System.out.println(((Map<String, Object>)l2.get(0)).get("name"));
        System.out.println(((Map<String, Object>)l2.get(0)).get("age"));

        List<Object> l3 = new ArrayList<Object>();
        User u1 = new User();
        u1.setName("andy");
        User u2 = new User();
        u2.setName("lucy");

        l3.add(u1);
        l3.add(u2);

        List<Object> l4 = (List<Object>)ObjectTranscoder.getInstance().deserialize(ObjectTranscoder.getInstance().serialize(l3));
        System.out.println(l4.size());
        System.out.println(((User)l4.get(0)).getName());

    }
}
