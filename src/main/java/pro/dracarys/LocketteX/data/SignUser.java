package pro.dracarys.LocketteX.data;

import com.google.gson.annotations.Expose;

import java.util.UUID;

public class SignUser {

    @Expose
    private final String name;
    @Expose
    private final String uuid;

    public SignUser(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public SignUser(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid.toString();
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return UUID.fromString(uuid);
    }
}
