module org.base_datos_viajes {
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    exports org.base_datos_viajes.config;
    exports org.base_datos_viajes.dao.interfaces;
    exports org.base_datos_viajes.util;
    exports org.base_datos_viajes.exception;
    // exports org.base_datos_viajes.model;
    // exports org.base_datos_viajes.dao.impl;
}
