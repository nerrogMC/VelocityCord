package net.nerrog.velocitycord.velocitycord.yaml;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConfigLoader {
    public static config LoadConfig(){
        //ファイルの確認、生成
        File configJson = new File("./plugins/VelocityCord/config.json");
        if(!configJson.exists()){
            try{
                new File("./plugins/VelocityCord/").mkdir();
                FileWriter fileWriter = new FileWriter(configJson);
                String defaltJson = "{\"BotToken\": \"\", \"ChannelId\": \"\"}";
                fileWriter.write(defaltJson);
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("./plugins/VelocityCord/config.json"), config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
