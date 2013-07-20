require dreambox-dvb-modules.inc

PR = "${INC_PR}.0"

SRC_URI[modules.md5sum] = "55bb3056e2ccaaf87382b36dbe052a69"
SRC_URI[modules.sha256sum] = "4d413a4e2140681b59696a7f06076dc731e468a99f340cfe6a1f0f1e5f2bd877"

RDEPENDS_${PN} += "kernel-module-stv0299"
