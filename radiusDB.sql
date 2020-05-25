CREATE TABLE radius.`property_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `price` double NOT NULL,
  `n_bedroom` int(11) NOT NULL,
  `n_bathroom` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lat_lon_price_room_idx` (`latitude`,`longitude`,`price`,`n_bedroom`,`n_bathroom`)
) ENGINE=InnoDB AUTO_INCREMENT=1000012 DEFAULT CHARSET=utf8;
