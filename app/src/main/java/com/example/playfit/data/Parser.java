package com.example.playfit.data;
import android.content.res.AssetManager;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class Parser {
    private UserDAOimpl users = new UserDAOimpl();

    public UserDAOimpl UserDAOimplParse() throws XmlPullParserException, IOException {
            XmlPullParserFactory parserFactory;
            try {
                parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserFactory.newPullParser();
               // InputStream is = getAssets().open("users.xml");
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
               // parser.setInput(is, null);

                processParsing(parser);

            } catch (XmlPullParserException e) {

            } catch (IOException e) {
            }

        return users;
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<UserDAOimpl> users = new ArrayList<>();
        int eventType = parser.getEventType();
        UserDTO user = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("player".equals(eltName)) {
                        user = new UserDTO();

                    } else if (user != null) {
                        if ("name".equals(eltName)) {
                            user.setUserName(parser.nextText());
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }


    }


}