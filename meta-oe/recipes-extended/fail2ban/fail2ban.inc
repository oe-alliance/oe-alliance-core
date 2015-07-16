SUMMARY = "Daemon to ban hosts that cause multiple authentication errors"
HOMEPAGE = "http://www.fail2ban.org"
SECTION = "devel/python"
BBCLASSEXTEND = "native"
RDPEPENDS_${PN} += " iptables "
SRC_URI = "git://github.com/fail2ban/fail2ban.git;branch=master"
S = "${WORKDIR}/git"
inherit setuptools

FILES_${PN} += "${sysconfdir}/* ${localstatedir}/* "
