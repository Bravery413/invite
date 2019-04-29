package utils;

import java.util.ArrayList;
import java.util.List;

public class IpMatcher {
    private List<IpConf> ipConfList = new ArrayList<>();

    private class IpConf {
        public long ip;
        public long mask;
    }

    public IpMatcher(String ipList) {
        initIpConfList(ipList);
    }

    public boolean match(String ip) {
        if (ipConfList.isEmpty()) {
            return true;
        }

        long ipLong = IpUtil.ipToLong(ip);
        for (IpConf ipConf : ipConfList) {
            if ((ipLong & ipConf.mask) == ipConf.ip) {
                return true;
            }
        }

        return false;
    }

    private void initIpConfList(String ipList) {
        if (StringUtils.isNullOrEmpty(ipList)) {
            return;
        }

        String[] ipArr = ipList.split(",");
        for (String ip : ipArr) {
            ip = ip.trim();
            if (ip.equals("")) {
                continue;
            }

            String ipStr = ip;
            long mask = 0xffffffff;

            int splash = ip.indexOf("/");
            if (splash >= 0) {
                ipStr = ip.substring(0, splash);
                int bits = StringUtils.parseInt(ip.substring(splash+1), -1) % 32;
                if (bits > 0) {
                    mask <<= 32 - bits;
                }
            }

            IpConf ipConf = new IpConf();
            ipConf.ip = IpUtil.ipToLong(ipStr) & mask;
            ipConf.mask = mask;
            ipConfList.add(ipConf);
        }
    }

    public static void main(String[] args) {
        System.out.println(new IpMatcher("123.1.4.0/16,123.2.0.0/16").match("123.2.123.243"));
    }
}
