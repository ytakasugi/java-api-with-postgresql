package jp.co.morgan.server.api;

import java.util.ArrayList;

import java.util.List;
//import java.sql.ResultSet;
import jp.co.morgan.server.dto.UserDto;

public class sample {
    public static void main(String[] args) {
        UserDto newUser = new UserDto();
        List<UserDto> list = new ArrayList<UserDto>();
        for (int i = 0; i < 10; i++) {
            String name = "test" + String.format("%03d", i);
            String email = "test" + String.format("%03d", i) + "@example.com";

            newUser.setUserName(name);
            newUser.setEMail(email);

            list.add(newUser);
            //System.out.println(name);
        }



        for (int i = 0; i < 10; i++) {
            UserDto getList = list.get(i);
            System.out.println(getList.getUserName());
        }
    }
}