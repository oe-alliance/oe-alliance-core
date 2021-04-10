SUMMARY = "Jedi EPG Xtream"
DESCRIPTION = "Assign 3rd Party EPG to IPTV Channels"
HOMEPAGE = "https://www.linuxsat-support.com/thread/141140-jedi-epg-xtream/"
MAINTAINER = "kiddac"
LICENSE = "GPLv3"
SECTION = "misc"
PRIORITY = "optional"

inherit allarch gitpkgv

SRCREV = "${AUTOREV}"
PV = "${VERSION}-git${SRCPV}"
PKGV = "${VERSION}-git${GITPKGV}"
PR = "r0"

BASE_URI = "github.com/kiddac/Jedi-EPG-XStream"
BRANCH = "main"
SRC_URI = "git://${BASE_URI}.git;protocol=git;branch=${BRANCH}"

CONFDIR = "${sysconfdir}/enigma2/jediepgxtream"
LIBDIR = "${libdir}/enigma2/python/Plugins/Extensions/JediEPGXtream"

FILES_${PN} = "${CONFDIR}/* ${LIBDIR}/*"

PACKAGES = "${PN}"


S = "${WORKDIR}/git"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${CONFDIR}
    install -d ${D}/${LIBDIR}
    cp -rf ${S}/JediEPGXtream/${CONFDIR}/* ${D}/${CONFDIR}/
    cp -rf ${S}/JediEPGXtream/${LIBDIR}/* ${D}/${LIBDIR}/
    rm -rf ${D}/${LIBDIR}/__pycache__
}


python() {
    import platform
    import re

    #
    # Get python version
    #
    MAJOR = 0
    MINOR = 1
    PATCH = 2

    python_version = platform.python_version_tuple()
    if python_version[MAJOR] == "2":
        import urllib2 as urllib
    elif python_version[MAJOR] == "3":
        import urllib.request as urllib

    #
    # Package version
    #
    BASE_URI = d.getVar("BASE_URI")
    SRCREV = d.getVar("SRCPV").replace("AUTOINC+", "")

    #
    # Get and set package version
    #
    URI = "https://{:s}/raw/{:s}/CONTROL/control".format(BASE_URI, SRCREV)
    socket = None
    data = "Version: 0.0-19700101"

    try:
        socket = urllib.urlopen(URI)
    except:
        pass
    if socket:
        data = socket.read().decode("utf8")
        socket.close()

    versionmatch = re.search(u"Version: (?P<version>.*)", data, re.IGNORECASE | re.UNICODE)
    if versionmatch:
        version = versionmatch.groupdict().get("version")

    d.setVar("VERSION", version)

    #
    # Get and set pkg_preinst
    #
    URI = "https://{:s}/raw/{:s}/CONTROL/preinst".format(BASE_URI, SRCREV)
    socket = None
    data = ""

    try:
        socket = urllib.urlopen(URI)
    except:
        pass
    if socket:
        data = socket.read().decode("utf8")
        socket.close()

    d.setVar("pkg_preinst_{}".format(d.getVar("PN")), data)

    #
    # Get and set pkg_postinst
    #
    URI = "https://{:s}/raw/{:s}/CONTROL/postinst".format(BASE_URI, SRCREV)
    socket = None
    data = ""

    try:
        socket = urllib.urlopen(URI)
    except:
        pass
    if socket:
        data = socket.read().decode("utf8")
        socket.close()

    d.setVar("pkg_postinst_{}".format(d.getVar("PN")), data)

    #
    # Get and set pkg_postrm
    #
    URI = "https://{:s}/raw/{:s}/CONTROL/postrm".format(BASE_URI, SRCREV)
    socket = None
    data = ""

    try:
        socket = urllib.urlopen(URI)
    except:
        pass
    if socket:
        data = socket.read().decode("utf8")
        socket.close()

    d.setVar("pkg_postrm_{}".format(d.getVar("PN")), data)
}
