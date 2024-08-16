DESCRIPTION = "IPTV m3u list dynamic reader and runner"
MAINTAINER = "DimitarCC"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"
HOMEPAGE = "https://github.com/DimitarCC"

inherit gitpkgv allarch ${PYTHON_PN}native gettext

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/DimitarCC/iptv-m3u-reader.git;protocol=https;branch=main"



S = "${WORKDIR}/git"

pluginpath = "/usr/lib/enigma2/python/Plugins/SystemPlugins/M3UIPTV"

do_install:append() {
	install -d ${D}${pluginpath}
	cp -r ${S}/src/* ${D}${pluginpath}/
	python3 -m compileall -o2 -b ${D}
	if [ -f /usr/bin/msgfmt ] ; then
		find ${S}/po/ -maxdepth 1 -type f -name '*.po' | while read po ; do
			## remove everything before and including the "/"
			filename=${po##*/}
			## remove everything after and including the "."
			cc=${filename%%.*}
			folder=${D}${pluginpath}/locale/${cc}/LC_MESSAGES
			mkdir -p ${folder}
			/usr/bin/msgfmt -o ${folder}/eitconfig.mo ${po}
		done
	fi
}

FILES:${PN} = "${pluginpath}/"

FILES:${PN}-src = "${pluginpath}/*.py"
