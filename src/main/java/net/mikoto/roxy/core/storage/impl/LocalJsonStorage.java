package net.mikoto.roxy.core.storage.impl;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.roxy.core.model.RoxyDataModel;
import net.mikoto.roxy.core.storage.Storage;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.util.FileUtil.*;

public class LocalJsonStorage implements Storage {
    private final String path;
    public LocalJsonStorage(String path) {
        this.path = path;
    }

    @Override
    public void store(String path, String name, RoxyDataModel roxyDataModel) throws IOException {
        createDir(this.path + path);
        writeFile(new File(this.path + path + "/" + name + ".json"), JSONObject.toJSONString(roxyDataModel));
    }
}
