PRINC = "3"

DEFAULT_TIMEZONE = "CET"

FILES_${PN} = "                                          \
                ${datadir}/zoneinfo/Africa/Casablanca    \
                ${datadir}/zoneinfo/Africa/Harare        \
                ${datadir}/zoneinfo/Africa/Kinshasa      \
                ${datadir}/zoneinfo/Africa/Nairobi       \
                ${datadir}/zoneinfo/America/Anchorage    \
                ${datadir}/zoneinfo/America/Bogota       \
                ${datadir}/zoneinfo/America/Caracas      \
                ${datadir}/zoneinfo/America/Danmarkshavn \
                ${datadir}/zoneinfo/America/Halifax      \
                ${datadir}/zoneinfo/America/Manaus       \
                ${datadir}/zoneinfo/America/Noronha      \
                ${datadir}/zoneinfo/America/Regina       \
                ${datadir}/zoneinfo/America/Santiago     \
                ${datadir}/zoneinfo/America/Sao_Paulo    \
                ${datadir}/zoneinfo/America/St_Johns     \
                ${datadir}/zoneinfo/America/Tijuana      \
                ${datadir}/zoneinfo/Asia/Almaty          \
                ${datadir}/zoneinfo/Asia/Baghdad         \
                ${datadir}/zoneinfo/Asia/Baku            \
                ${datadir}/zoneinfo/Asia/Bangkok         \
                ${datadir}/zoneinfo/Asia/Calcutta        \
                ${datadir}/zoneinfo/Asia/Colombo         \
                ${datadir}/zoneinfo/Asia/Dhaka           \
                ${datadir}/zoneinfo/Asia/Hong_Kong       \
                ${datadir}/zoneinfo/Asia/Irkutsk         \
                ${datadir}/zoneinfo/Asia/Jerusalem       \
                ${datadir}/zoneinfo/Asia/Kabul           \
                ${datadir}/zoneinfo/Asia/Kathmandu       \
                ${datadir}/zoneinfo/Asia/Kolkata         \
                ${datadir}/zoneinfo/Asia/Krasnoyarsk     \
                ${datadir}/zoneinfo/Asia/Kuala_Lumpur    \
                ${datadir}/zoneinfo/Asia/Magadan         \
                ${datadir}/zoneinfo/Asia/Muscat          \
                ${datadir}/zoneinfo/Asia/Omsk            \
                ${datadir}/zoneinfo/Asia/Rangoon         \
                ${datadir}/zoneinfo/Asia/Riyadh          \
                ${datadir}/zoneinfo/Asia/Seoul           \
                ${datadir}/zoneinfo/Asia/Taipei          \
                ${datadir}/zoneinfo/Asia/Tashkent        \
                ${datadir}/zoneinfo/Asia/Tehran          \
                ${datadir}/zoneinfo/Asia/Tokyo           \
                ${datadir}/zoneinfo/Asia/Vladivostok     \
                ${datadir}/zoneinfo/Asia/Yakutsk         \
                ${datadir}/zoneinfo/Atlantic/Azores      \
                ${datadir}/zoneinfo/Atlantic/Cape_Verde  \
                ${datadir}/zoneinfo/Australia/Adelaide   \
                ${datadir}/zoneinfo/Australia/Brisbane   \
                ${datadir}/zoneinfo/Australia/Darwin     \
                ${datadir}/zoneinfo/Australia/Hobart     \
                ${datadir}/zoneinfo/Australia/Perth      \
                ${datadir}/zoneinfo/Australia/Sydney     \
                ${datadir}/zoneinfo/Brazil/Acre          \
                ${datadir}/zoneinfo/Brazil/DeNoronha     \
                ${datadir}/zoneinfo/Brazil/East          \
                ${datadir}/zoneinfo/Brazil/West          \
                ${datadir}/zoneinfo/Canada/Newfoundland  \
                ${datadir}/zoneinfo/Canada/Saskatchewan  \
                ${datadir}/zoneinfo/CET                  \
                ${datadir}/zoneinfo/CST6CDT              \
                ${datadir}/zoneinfo/EST                  \
                ${datadir}/zoneinfo/EST5EDT              \
                ${datadir}/zoneinfo/Europe/Dublin        \
                ${datadir}/zoneinfo/Europe/Istanbul      \
                ${datadir}/zoneinfo/Europe/Moscow        \
                ${datadir}/zoneinfo/MST                  \
                ${datadir}/zoneinfo/MST7MDT              \
                ${datadir}/zoneinfo/Pacific/Auckland     \
                ${datadir}/zoneinfo/Pacific/Fiji         \
                ${datadir}/zoneinfo/Pacific/Guam         \
                ${datadir}/zoneinfo/Pacific/Honolulu     \
                ${datadir}/zoneinfo/Pacific/Kwajalein    \
                ${datadir}/zoneinfo/Pacific/Midway       \
                ${datadir}/zoneinfo/Pacific/Pacific/Norfolk \
                ${datadir}/zoneinfo/Pacific/Tongatapu    \
                ${sysconfdir}/localtime                  \
                ${sysconfdir}/timezone                   "

PACKAGES += "${PN}-base"

FILES_${PN}-base = "${datadir}/zoneinfo"
