SUMMARY = "TBS Si2183 multi-standard frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/si2183.c \
    drivers/media/dvb-frontends/si2183.h \
"
TBS_KBUILD_MODULES = "si2183.o"
TBS_INSTALL_MODULES = "si2183"
TBS_DVB_ENUM_COMPAT = " \
    APSK_8_L=QAM_AUTO \
    APSK_16_L=APSK_16 \
    APSK_32_L=APSK_32 \
    FEC_1_3=FEC_AUTO \
    FEC_1_4=FEC_AUTO \
    ROLLOFF_15=ROLLOFF_AUTO \
    ROLLOFF_10=ROLLOFF_AUTO \
    ROLLOFF_5=ROLLOFF_AUTO \
"

RDEPENDS:${PN} += "firmware-dvb-demod-si2183"
