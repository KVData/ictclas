package cn.edu.nju.software.mongo;

/**
 * Created by Administrator on 2017/10/29.
 */
public class User {
    String name;
    int age;
    Oid _id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * Mongodb会自动生成ObjectId
     * @author fhp
     *
     */
    public class Oid{
        String $oid;
        public String get$oid() {
            return $oid;
        }

        public void set$oid(String $oid) {
            this.$oid = $oid;
        }

    }
}
