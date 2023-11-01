package net.limbomedia.esp.x.mgmt;

import net.limbomedia.esp.db.AppEntity;
import net.limbomedia.esp.db.DeviceEntity;
import net.limbomedia.esp.db.ImageDataEntity;
import net.limbomedia.esp.db.VersionEntity;
import net.limbomedia.esp.x.common.api.ImageData;
import net.limbomedia.esp.x.common.api.Version;
import net.limbomedia.esp.x.mgmt.api.App;
import net.limbomedia.esp.x.mgmt.api.Device;

public class Mapper {

    public static Device map(DeviceEntity from) {
        Device to = new Device();
        to.setId(from.getId());
        to.setState(from.getState());
        to.setUuid(from.getUuid());
        to.setSecret(from.getSecret());
        to.setName(from.getName());
        to.setSource(from.getSource());
        to.setPlatform(from.getPlatform());
        to.setTsCheck(from.getTsCheck());
        to.setTsCreate(from.getTsCreate());
        to.setTsUpdate(from.getTsUpdate());
        to.setInfo(from.getInfo());
        to.setHashApp(from.getHashFirmware());
        if (from.getApp() != null) {
            to.setApp(map(from.getApp()));
            VersionEntity versionLatest = from.getApp().getVersionLatest();
            if (versionLatest != null) {
                to.setVersionLatest(map(versionLatest));
            }
            if (from.getVersion() != null) {
                to.setVersionCurrent(map(from.getVersion()));
            }
        }

        if (from.getImageData() != null) {
            to.setImageData(map(from.getImageData()));
        }

        return to;
    }

    public static App map(AppEntity from) {
        App to = new App();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setPlatform(from.getPlatform());
        to.setKey(from.getKey());
        return to;
    }

    public static Version map(VersionEntity from) {
        Version to = new Version();
        to.setId(from.getId());
        to.setNr(from.getNr());
        to.setName(from.getName());
        to.setTs(from.getTs());
        to.setBinId(from.getBinId());
        to.setBinSize(from.getBinSize());
        to.setBinHash(from.getBinHash());
        return to;
    }

    public static ImageData map(ImageDataEntity from) {
        ImageData to = new ImageData();
        to.setId(from.getId());
        to.setNr(from.getNr());
        to.setName(from.getName());
        to.setTs(from.getTs());
        to.setTsFetch(from.getTsFetch());
        to.setBinId(from.getBinId());
        to.setBinSize(from.getBinSize());
        to.setBinHash(from.getBinHash());
        return to;
    }
}
