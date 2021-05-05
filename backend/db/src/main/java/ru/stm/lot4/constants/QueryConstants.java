package ru.stm.lot4.constants;

public final class QueryConstants {
    public static final String SELECT_APPLICATION_STATISTIC =
            "select " +
                    " ap.version as appVersion, " +
                    " COUNT(distinct p.number) as phoneNumbersCount, " +
                    " max(x.registered) as registrationsCount " +
            "from mobile_application ap  " +
            "left join phone p  " +
            "on p.app_id = ap.id  " +
            "left join (select x.ver, sum(pnum) registered from (select amp.version ver, count(pn.number) pnum " +
            "from mobile_application amp  " +
            "left join phone pn " +
            "on pn.app_id = amp.id " +
            "group by amp.version) x group by x.ver) x " +
            "on x.ver = ap.version where p.is_active = true " +
            "group by ap.version";
    public static final String SELECT_RECEIVED_MESSAGES_BY_PHONE = "select pn.* from push_notification as pn " +
            " left join push_notification_phone as pnp on pnp.push_notification_id = pn.id " +
            " left join phone as p on pnp.phone_id = p.id where p.number = :phone and pn.status = :status";
}
