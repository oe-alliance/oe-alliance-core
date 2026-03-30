SUMMARY = "TBS M88RS6060 DVB-S/S2/S2X frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/m88rs6060.c \
    drivers/media/dvb-frontends/m88rs6060.h \
    drivers/media/dvb-frontends/m88rs6060_priv.h \
"
TBS_KBUILD_MODULES = "m88rs6060.o"
TBS_INSTALL_MODULES = "m88rs6060"
TBS_CCFLAGS = "-DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
TBS_DVB_ENUM_COMPAT = " \
    APSK_64=QAM_AUTO \
    FEC_1_4=FEC_AUTO \
    FEC_1_3=FEC_AUTO \
    FEC_5_9=FEC_AUTO \
    FEC_7_9=FEC_AUTO \
    FEC_4_15=FEC_AUTO \
    FEC_7_15=FEC_AUTO \
    FEC_8_15=FEC_AUTO \
    FEC_11_15=FEC_AUTO \
    FEC_13_18=FEC_AUTO \
    FEC_9_20=FEC_AUTO \
    FEC_11_20=FEC_AUTO \
    FEC_23_36=FEC_AUTO \
    FEC_25_36=FEC_AUTO \
    FEC_11_45=FEC_AUTO \
    FEC_13_45=FEC_AUTO \
    FEC_14_45=FEC_AUTO \
    FEC_26_45=FEC_AUTO \
    FEC_28_45=FEC_AUTO \
    FEC_29_45=FEC_AUTO \
    FEC_31_45=FEC_AUTO \
    FEC_32_45=FEC_AUTO \
    FEC_77_90=FEC_AUTO \
    ROLLOFF_15=ROLLOFF_AUTO \
    ROLLOFF_10=ROLLOFF_AUTO \
    ROLLOFF_5=ROLLOFF_AUTO \
"

RDEPENDS:${PN} += "firmware-dvb-demod-m88rs6060"
