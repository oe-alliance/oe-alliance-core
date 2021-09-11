SUMMARY = "An asynchronous networking framework written in Python"
DESCRIPTION = "Twisted is an event-driven networking framework written in Python and licensed under the LGPL. \
Twisted supports TCP, UDP, SSL/TLS, multicast, Unix sockets, a large number of protocols                   \
(including HTTP, NNTP, IMAP, SSH, IRC, FTP, and others), and much more."
HOMEPAGE = "http://www.twistedmatrix.com"
SECTION = "devel/python"

#twisted/topfiles/NEWS:655: - Relicensed: Now under the MIT license, rather than LGPL.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=27ac6d9597237e7e76282edd7a40cd68"

SRC_URI[sha256sum] = "d72c55b5d56e176563b91d11952d13b01af8725c623e498db5507b6614fc1e10"
SRC_URI[md5sum] = "fc16d575730db7d0cddd09fc35af3eea"

FILESEXTRAPATHS_prepend := "${THISDIR}/python-twisted:"

SRC_URI += " \
    file://0001-Revert-Prevent-CRLF-injections-described-in-CVE-2019.patch \
    "

PYPI_PACKAGE = "Twisted"
PYPI_PACKAGE_EXT = "tar.bz2"

inherit pypi python-dir setuptools

do_install_append() {
    # remove some useless files before packaging
    find ${D} \( -name "*.bat" -o -name "*.c" -o -name "*.h" \) -exec rm -f {} \;
}

PACKAGES += "\
    ${PN}-zsh \
    ${PN}-test \
    ${PN}-protocols \
    ${PN}-conch \
    ${PN}-mail \
    ${PN}-names \
    ${PN}-news \
    ${PN}-runner \
    ${PN}-web \
    ${PN}-words \
    ${PN}-flow \
    ${PN}-pair \
    ${PN}-core \
"

PACKAGES =+ "\
    ${PN}-bin \
"

DEPENDS += " \
    ${PYTHON_PN}-incremental-native \
"

RDEPENDS_${PN} = "\
    ${PN}-bin \
    ${PN}-core \
    ${PN}-conch \
    ${PN}-mail \
    ${PN}-names \
    ${PN}-pair \
    ${PN}-protocols \
    ${PN}-runner \
    ${PN}-web \
    ${PN}-words \
    ${PN}-zsh \
    ${PN}-news \
"

RDEPENDS_${PN}-core = " \
    ${PYTHON_PN}-automat \
    ${PYTHON_PN}-constantly \
    ${PYTHON_PN}-core \
    ${PYTHON_PN}-debugger \
    ${PYTHON_PN}-hyperlink \
    ${PYTHON_PN}-incremental \
    ${PYTHON_PN}-misc \
    ${PYTHON_PN}-pkg-resources \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-plistlib \
    ${PYTHON_PN}-pyhamcrest \
    ${PYTHON_PN}-pyserial \
    ${PYTHON_PN}-setuptools \
    ${PYTHON_PN}-unixadmin \
    ${PYTHON_PN}-zopeinterface \
    ${PYTHON_PN}-contextlib \
    ${PYTHON_PN}-service-identity \
"

RDEPENDS_${PN}-test = "${PN}"

RDEPENDS_${PN}-conch = " \
    ${PN}-core \
    ${PN}-protocols \
    ${PYTHON_PN}-bcrypt \
    ${PYTHON_PN}-cryptography \
    ${PYTHON_PN}-pickle \
    ${PYTHON_PN}-pyasn1 \
"

RDEPENDS_${PN}-mail = "${PN}-core ${PN}-protocols"
RDEPENDS_${PN}-names = "${PN}-core"
RDEPENDS_${PN}-news = "${PN}-core ${PN}-protocols"
RDEPENDS_${PN}-runner = "${PN}-core ${PN}-protocols"
RDEPENDS_${PN}-web += "${PN}-core ${PN}-protocols"
RDEPENDS_${PN}-words += "${PN}-core"
RDEPENDS_${PN}-flow += "${PN}-core"
RDEPENDS_${PN}-pair += "${PN}-core"
RDEPENDS_${PN}-dbg = "${PN}"

ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = ""

FILES_${PN}-test = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/test \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/haproxy/test/ \
"

FILES_${PN}-protocols = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/*.py* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/gps/ \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/mice/ \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/haproxy \
"

FILES_${PN}-zsh = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/zsh \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/zshcomp.* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/twisted-completion.zsh \
"

FILES_${PN}-conch = " \
    ${bindir}/ckeygen \
    ${bindir}/tkconch \
    ${bindir}/conch \
    ${bindir}/conchftp \
    ${bindir}/cftp \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_conch.py* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/conch  \
"

FILES_${PN}-core = " \
${bindir}/manhole \
${bindir}/mktap \
${bindir}/twistd \
${bindir}/tap2deb \
${bindir}/tap2rpm \
${bindir}/tapconvert \
${bindir}/tkmktap \
${bindir}/trial \
${bindir}/easy_install* \
${bindir}/pyhtmlizer \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/*.so \
${PYTHON_SITEPACKAGES_DIR}/twisted/*.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__init__.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/notestplugin.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/testplugin.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_ftp.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_inet.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_manhole.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_portforward.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_socks.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_telnet.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_trial.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/dropin.cache \
${PYTHON_SITEPACKAGES_DIR}/twisted/application \
${PYTHON_SITEPACKAGES_DIR}/twisted/cred \
${PYTHON_SITEPACKAGES_DIR}/twisted/enterprise \
${PYTHON_SITEPACKAGES_DIR}/twisted/internet \
${PYTHON_SITEPACKAGES_DIR}/twisted/manhole \
${PYTHON_SITEPACKAGES_DIR}/twisted/manhole \
${PYTHON_SITEPACKAGES_DIR}/twisted/persisted \
${PYTHON_SITEPACKAGES_DIR}/twisted/protocols\
${PYTHON_SITEPACKAGES_DIR}/twisted/python\
${PYTHON_SITEPACKAGES_DIR}/twisted/python/timeoutqueue.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/filepath.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/dxprofile.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/plugin.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/htmlizer.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/__init__.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/dispatch.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/hook.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/threadpool.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/otp.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/usage.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/roots.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/versions.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/urlpath.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/util.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/components.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/logfile.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/runtime.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/reflect.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/context.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/threadable.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/rebuild.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/failure.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/lockfile.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/formmethod.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/finalize.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/win32.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/dist.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/shortcut.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/zipstream.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/release.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/syslog.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/log.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/compat.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/zshcomp.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/procutils.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/text.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/_twisted_zsh_stub \
${PYTHON_SITEPACKAGES_DIR}/twisted/scripts/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/spread/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/tap/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/trial/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/__init__.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/_version.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/copyright.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/im.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/*.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/python/*.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/*.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/topfiles \
${PYTHON_SITEPACKAGES_DIR}/Twisted*egg-info \
${PYTHON_SITEPACKAGES_DIR}/twisted/logger/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/_threads/ \
${PYTHON_SITEPACKAGES_DIR}/twisted/positioning/ \
"

FILES_${PN}-mail = " \
${bindir}/mailmail \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_mail.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/mail \
"

FILES_${PN}-names = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_names.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/names \
"

FILES_${PN}-news = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_news.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/news \
"

FILES_${PN}-runner = " \
${libdir}/site-packages/twisted/runner/portmap.so \
${PYTHON_SITEPACKAGES_DIR}/twisted/runner\
"

FILES_${PN}-web = " \
${bindir}/websetroot \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_web.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/web\
"

FILES_${PN}-words = " \
${bindir}/im \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_words.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/words\
"

FILES_${PN}-flow = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_flow.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/flow \"

FILES_${PN}-pair = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_pair.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/pair \
"

FILES_${PN}-dbg += " \
${PYTHON_SITEPACKAGES_DIR}/twisted/*/.debug \
${PYTHON_SITEPACKAGES_DIR}/twisted/*/*/.debug \
${PYTHON_SITEPACKAGES_DIR}/twisted/*.egg-info \
${PYTHON_SITEPACKAGES_DIR}/twisted/*/*/test \
"

FILES_${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/_pydoctortemplates/ \
"

RDEPENDS_${PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/*/*/*.py \
"

