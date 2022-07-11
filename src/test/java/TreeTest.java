package test.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import ru.ac.uniyar.mf.lomtev.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTest {
    @Test
    void createNode(){
        Node node = new Node("Корень");
        Assertions.assertEquals("Корень", node.getName());
    }

    @Test
    void addNode(){
        Node root = new Node("Корень");
        Node child = new Node("Лист");
        root.add(child);
        Assertions.assertEquals(1, root.getChildren().size());
        Assertions.assertEquals("Лист", root.getChildren().get(0).getName());
    }

    @Test
    void deleteNode(){
        Node root1 = new Node("Корень");
        Node child1 = new Node("Лист1");
        Node child2 = new Node("Лист2");
        Node child3 = new Node("Лист3");
        root1.add(child1);
        root1.add(child2);
        root1.add(child3);

        Node root2 = new Node("Корень");
        root2.add(child1);
        root2.add(child2);

        root1.deleteNode(child3.id);
        Assertions.assertEquals(root1.children, root2.children);
    }

    @Test
    void write_tree(){
        Node root1 = new Node("Корень");
        Node child1 = new Node("Лист1");
        root1.add(child1);
        String actual = root1.toString();
        String expected = "Корень\n\tЛист1\n";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void write_tree_to_file() throws IOException {
        Node root1 = new Node("Корень");
        Node child1 = new Node("Лист1");
        root1.add(child1);
        String actual = root1.toString();
        String path = "/home/user/Рабочий стол/gg/yy.txt";
        Files.write(Paths.get(path), actual.getBytes());
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String expected = new String (bytes);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void print2Json() throws JsonProcessingException {
        Node root = new Node("Корень");
        Node child = new Node("Лист");
        root.add(child);
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String actual = objectMapper.writeValueAsString(root);
        System.out.println(actual);
    }

    @Test
    void readFromJson() throws JsonProcessingException {
        String jsonString = "{\n" +
                " \"name\" : \"Корень\", \n" +
                " \"children\" : [ {\n" +
                "   \"name\" : \"Лист\", \n" +
                "   \"children\" : [ ]\n" +
                " } ]\n" +
                "}";
        ;
        ObjectMapper objectMapper = new ObjectMapper();
        Node actual = objectMapper.readValue(jsonString, Node.class);
        Assertions.assertEquals("Корень", actual.getName());
        Assertions.assertEquals(1, actual.getChildren().size());
        Assertions.assertEquals("Лист", actual.getChildren().get(0).getName());
    }
}