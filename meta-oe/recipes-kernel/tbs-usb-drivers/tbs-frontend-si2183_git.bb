SUMMARY = "TBS Si2183 multi-standard frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/si2183.c \
    drivers/media/dvb-frontends/si2183.h \
"
TBS_KBUILD_MODULES = "si2183.o"
TBS_INSTALL_MODULES = "si2183"
TBS_CCFLAGS = " \
    -DAPSK_8_L=QAM_AUTO \
    -DAPSK_16_L=APSK_16 \
    -DAPSK_32_L=APSK_32 \
    -DFEC_1_3=FEC_AUTO \
    -DFEC_1_4=FEC_AUTO \
    -DROLLOFF_15=ROLLOFF_AUTO \
    -DROLLOFF_10=ROLLOFF_AUTO \
    -DROLLOFF_5=ROLLOFF_AUTO \
"

RDEPENDS:${PN} += "firmware-dvb-demod-si2183"
