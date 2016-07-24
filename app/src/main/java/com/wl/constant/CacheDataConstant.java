package com.wl.constant;

import com.wl.bean.AddressBean;
import com.wl.bean.ConsigneesBean;
import com.wl.bean.CustomerBean;
import com.wl.bean.NotifyBean;
import com.wl.bean.OrderBean;
import com.wl.bean.OrderEntity;
import com.wl.bean.PortsEntity;
import com.wl.bean.PriceEntity;
import com.wl.bean.ShipCompanyEntity;
import com.wl.bean.ShipperBean;
import com.wl.bean.UserBean;
import com.wl.bean.VoyageEntity;
import com.wl.view.LockPatternView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by taoy2 on 15-9-24.
 */
public class CacheDataConstant {
    public static List<PortsEntity> ports = new ArrayList<>();
    public static List<ShipCompanyEntity> companies = new ArrayList<>();
    public static List<LockPatternView.Cell> choosePattern;
    public static UserBean userBean;
    public static VoyageEntity voyageEntity;
    public static List<CustomerBean> customers = new ArrayList<>();
    public static List<AddressBean> addressBeans = new ArrayList<>();
    public static HashMap<String,List<ShipperBean>> shippers = new HashMap<>();
    public static HashMap<String,List<ConsigneesBean>> consignees = new HashMap<>();
    public static HashMap<String,List<NotifyBean>> notify = new HashMap<>();
    public static HashMap<Integer,List<OrderEntity>> orderList = new HashMap<>();
    public static OrderBean orderBean;
    public static String address;
    public static PriceEntity price;
    public static List<String> currCodes = new ArrayList<>();
}
