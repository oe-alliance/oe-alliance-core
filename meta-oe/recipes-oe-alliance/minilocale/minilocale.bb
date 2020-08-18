SUMMARY = "Size reduced but fully functional locale support"
MAINTAINER = "OE-Alliance"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

PKGV = "0.12"
#PR = "r0"

require conf/license/license-gplv2.inc

SRC_URI = "file://locales.tar.gz file://locale.alias file://c-utf-8.tar.gz file://locale.sh"

S = "${WORKDIR}/usr/lib/locale"

LOCALEDIR = "${libdir}/locale"
LOCALEDIR2 = "/usr/share/locale"

LANGUAGES = "ar_AE bg_BG ca_AD cs_CZ da_DK de_DE el_GR en_GB en_US es_ES et_EE fa_IR fi_FI fr_FR \
             fy_NL he_IL hr_HR hu_HU id_ID is_IS it_IT lt_LT lv_LV nb_NO nl_NL pl_PL pt_BR pt_PT \
             ru_RU sk_SK sl_SI sr_RS sv_SE th_TH tr_TR uk_UA zh_CN zh_HK"

RPROVIDES_${PN}  = "${@" ".join(map(lambda s: "virtual-locale-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RPROVIDES_${PN} += "${@" ".join("virtual-locale-%s" % p.split('_')[0] for p in d.getVar('LANGUAGES').split())}"
RPROVIDES_${PN} += "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RCONFLICTS_${PN} = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RREPLACES_${PN}  = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"

do_install() {
	install -d ${D}${sysconfdir}/profile.d
	install -m 0644 ${WORKDIR}/locale.sh ${D}${sysconfdir}/profile.d/locale.sh
	install -d ${D}${LOCALEDIR2}
	install ${WORKDIR}/locale.alias ${D}${LOCALEDIR2}

	install -d ${D}${LOCALEDIR}
	cp -rp ${S}/* ${D}/${LOCALEDIR}

	for langpath in $(find ${D}${LOCALEDIR}/* -maxdepth 1 -type d); do
		lang=$(basename $langpath)
		if [ "x$lang" != "xC.UTF-8" -a "x$lang" != "xlocale" -a "x$lang" != "xLC_MESSAGES" ]; then
			# For machines with up to 256 MB flash use the LC_COLLATE and LC_CTYPE from C.UTF-8 for all languages
			if [ ${FLASHSIZE} -le 256 ]; then
		                [ -e ${D}${LOCALEDIR}/$lang/LC_CTYPE ] && rm -f ${D}${LOCALEDIR}/$lang/LC_CTYPE
			        [ -e ${D}${LOCALEDIR}/$lang/LC_COLLATE ] && rm -f ${D}${LOCALEDIR}/$lang/LC_COLLATE
		        fi
	                [ ! -e ${D}${LOCALEDIR}/$lang/LC_CTYPE ] && ln -sf ../C.UTF-8/LC_CTYPE ${D}${LOCALEDIR}/$lang/LC_CTYPE
		        [ ! -e ${D}${LOCALEDIR}/$lang/LC_COLLATE ] && ln -sf ../C.UTF-8/LC_COLLATE ${D}${LOCALEDIR}/$lang/LC_COLLATE
		fi
	done

	# For machines with only 64 MB of flash delete all LC_COLLATE and use POSIX LC_COLLATE instead
	if [ ${FLASHSIZE} -le 96 ]; then
		find ${D}${LOCALEDIR}/ -name 'LC_COLLATE' \( -type f -o -type l \) -exec rm -f {} +
	fi

	ln -s en_GB ${D}${LOCALEDIR}/en_EN
	ln -s nb_NO ${D}${LOCALEDIR}/no_NO
	ln -s sr_RS ${D}${LOCALEDIR}/sr_YU
}

FILES_${PN} = "${LOCALEDIR} ${LOCALEDIR2} ${sysconfdir}/profile.d"

do_package_qa[noexec] = "1"
