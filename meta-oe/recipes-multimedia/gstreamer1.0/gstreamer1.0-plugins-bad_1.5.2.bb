include gstreamer1.0-plugins-bad.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://gst/tta/filters.h;beginline=12;endline=29;md5=8a08270656f2f8ad7bb3655b83138e5a \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 \
                    file://gst/tta/crc32.h;beginline=12;endline=29;md5=27db269c575d1e5317fffca2d33b3b50"

SRC_URI += " \
            file://0001-rtmp-fix-seeking-and-potential-segfault.patch \
            file://dvbapi5-fix-old-kernel.patch \
"

SRC_URI[md5sum] = "8735273f25226190827826cb0966a888"
SRC_URI[sha256sum] = "380ca225531136e249c4748abee855eb0c93090abdea4808703a58028c65113f"

S = "${WORKDIR}/gst-plugins-bad-${PV}"

TARGET_CFLAGS_append = " -Wno-error=maybe-uninitialized"
