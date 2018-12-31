FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PV = "4.0.2+gitr${SRCPV}"

SRCREV = "8adc786bac9234fc298c941dd442c3af3155a522"
SRC_URI = "git://github.com/Pulse-Eight/libcec.git \
           file://0001-multilib.patch"

EXTRA_OECMAKE += " -DBASE_LIB=${libdir}"

RDEPENDS_${PN} += "python"
RDEPENDS_${PN}-tools += "python"
RDEPENDS_${PN}_remove = "python3-core"
RDEPENDS_python3-libcec += "python"
RDEPENDS_python3-libcec_remove = "python3-core"
