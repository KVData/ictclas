package cn.edu.nju.software;

import cn.edu.nju.software.service.NlpService;
import cn.edu.nju.software.service.NlpServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author dalec
 */
public class Main {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("comments.json"));
             NlpService service = new NlpServiceImpl()) {
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                content.append(line);
            }
            System.out.println(service.getKeyWords(content.toString(), 10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
