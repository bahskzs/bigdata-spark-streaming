package domain;

/**
 * @author bahsk
 * @createTime 2021-02-14 0:56
 * @description
 */
public class Access {

    private int id;
    private String name;
    private String time;

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
