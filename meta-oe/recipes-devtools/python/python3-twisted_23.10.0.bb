DESCRIPTION = "Twisted is an event-driven networking framework written in Python and licensed under the LGPL. \
Twisted supports TCP, UDP, SSL/TLS, multicast, Unix sockets, a large number of protocols                   \
(including HTTP, NNTP, IMAP, SSH, IRC, FTP, and others), and much more."
HOMEPAGE = "http://www.twistedmatrix.com"

#twisted/topfiles/NEWS:655: - Relicensed: Now under the MIT license, rather than LGPL.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ffa49c5e50e2647e2813c5f2af317fbd"

SRC_URI[sha256sum] = "987847a0790a2c597197613686e2784fd54167df3a55d0fb17c8412305d76ce5"

PYPI_PACKAGE = "twisted"

inherit pypi python_setuptools_build_meta

do_install:append() {
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
    ${PYTHON_PN}-incremental-native ${PYTHON_PN}-hatchling-native ${PYTHON_PN}-hatch-fancy-pypi-readme-native \
"

RDEPENDS:${PN} = "\
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
"

RDEPENDS:${PN}-core = "${PYTHON_PN}-appdirs \
                       ${PYTHON_PN}-asyncio \
                       ${PYTHON_PN}-automat \
                       ${PYTHON_PN}-constantly \
                       ${PYTHON_PN}-core \
                       ${PYTHON_PN}-debugger \
                       ${PYTHON_PN}-hyperlink \
                       ${PYTHON_PN}-incremental \
                       ${PYTHON_PN}-pyhamcrest \
                       ${PYTHON_PN}-pyserial \
                       ${PYTHON_PN}-typing-extensions \
                       ${PYTHON_PN}-unixadmin \
                       ${PYTHON_PN}-zopeinterface \
"
RDEPENDS:${PN}-test = "${PN}"
RDEPENDS:${PN}-conch = "${PN}-core ${PN}-protocols ${PYTHON_PN}-bcrypt ${PYTHON_PN}-cryptography ${PYTHON_PN}-pyasn1 ${PYTHON_PN}-pickle"
RDEPENDS:${PN}-mail = "${PN}-core ${PN}-protocols"
RDEPENDS:${PN}-names = "${PN}-core"
RDEPENDS:${PN}-news = "${PN}-core ${PN}-protocols"
RDEPENDS:${PN}-runner = "${PN}-core ${PN}-protocols"
RDEPENDS:${PN}-web += "${PN}-core ${PN}-protocols"
RDEPENDS:${PN}-words += "${PN}-core"
RDEPENDS:${PN}-flow += "${PN}-core"
RDEPENDS:${PN}-pair += "${PN}-core"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}/${PYPI_PACKAGE}-${PV}.dist-info/*"

FILES:${PN}-test = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/test \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/haproxy/test/ \
"

FILES:${PN}-protocols = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/*.py* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/gps/ \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/mice/ \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/haproxy \
"

FILES:${PN}-zsh = " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/zsh \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/zshcomp.* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/twisted-completion.zsh \
"

FILES:${PN}-conch = " \
    ${bindir}/ckeygen \
    ${bindir}/tkconch \
    ${bindir}/conch \
    ${bindir}/conchftp \
    ${bindir}/cftp \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_conch.py* \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/conch  \
"

FILES:${PN}-core = " \
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
${PYTHON_SITEPACKAGES_DIR}/twisted/py.typed \
"

FILES:${PN}-mail = " \
${bindir}/mailmail \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_mail.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/mail \
"

FILES:${PN}-names = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_names.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/names \
"

FILES:${PN}-news = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_news.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/news \
"

FILES:${PN}-runner = " \
${libdir}/site-packages/twisted/runner/portmap.so \
${PYTHON_SITEPACKAGES_DIR}/twisted/runner\
"

FILES:${PN}-web = " \
${bindir}/websetroot \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_web.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/web\
"

FILES:${PN}-words = " \
${bindir}/im \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_words.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/words\
"

FILES:${PN}-flow = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_flow.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/flow \"

FILES:${PN}-pair = " \
${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/twisted_pair.py* \
${PYTHON_SITEPACKAGES_DIR}/twisted/pair \
"

FILES:${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/twisted/python/_pydoctortemplates/ \
"

FILES:${PN}-core:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/__pycache__ \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/python/__pycache__/*pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/__init__*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/notestplugin*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/testplugin*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_ftp*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_inet*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_manhole*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_portforward*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_socks*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_telnet*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_trial*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_core*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_qtstub*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_reactors*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/cred*.pyc \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/dropin*.cache \
"

FILES:${PN}-names:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_names*.pyc \
"

FILES:${PN}-news:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_news*.pyc \
"

FILES:${PN}-protocols:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/protocols/__pycache__/*pyc \
"

FILES:${PN}-conch:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_conch*.pyc \
"

FILES:${PN}-lore:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_lore*.pyc \
"
FILES:${PN}-mail:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_mail*.pyc \
"

FILES:${PN}-web:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_web*.pyc \
"

FILES:${PN}-words:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_words*.pyc \
"

FILES:${PN}-flow:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_flow*.pyc \
"

FILES:${PN}-pair:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_pair*.pyc \
"

FILES:${PN}-runner:append = " \
  ${PYTHON_SITEPACKAGES_DIR}/twisted/plugins/__pycache__/twisted_runner*.pyc \
"
