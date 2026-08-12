SUMMARY = "Size reduced but fully functional locale support"
MAINTAINER = "OE-Alliance"
LICENSE = "GPL-2.0-only"
SECTION = "base"
PRIORITY = "required"

PKGV = "2.44"

require conf/license/license-gplv2.inc

SRC_URI = "file://locales.tar.gz file://locale.alias file://c-utf-8.tar.gz file://locale.sh"

S = "${UNPACKDIR}/usr/lib/locale"

LOCALEDIR = "${libdir}/locale"
LOCALEDIR2 = "/usr/share/locale"

LANGUAGES = "ar_AE bg_BG ca_AD cs_CZ da_DK de_DE el_GR en_AU en_GB en_US es_ES et_EE \
             fa_IR fi_FI fr_FR fy_NL he_IL hr_HR hu_HU id_ID is_IS it_IT lt_LT lv_LV \
             nb_NO nn_NO nl_NL pl_PL pt_BR pt_PT ru_RU sk_SK sl_SI sr_RS sv_SE th_TH \
             tr_TR uk_UA zh_CN zh_HK ar_EG ar_BH ar_DJ ar_DZ ar_EH ar_ER ar_IL ar_IQ \
             ar_JO ar_KM ar_KW ar_LB ar_LY ar_MA ar_MR ar_OM ar_PS ar_QA ar_SA ar_SD \
             ar_SO ar_SS ar_SY ar_TD ar_TN ar_YE ca_ES ca_FR ca_IT de_AT de_BE de_CH \
             de_IT de_LI de_LU el_CY en_AG en_BW en_BZ en_CA en_DK en_HK en_IE en_IL \
             en_IN en_JM en_KH en_NG en_NZ en_PH en_SC en_SG en_TT en_ZA en_ZM en_ZW \
             es_AR es_BO es_CL es_CO es_CR es_CU es_DO es_EC es_GT es_HN es_MX es_NI \
             es_PA es_PE es_PR es_PY es_SV es_US es_UY es_VE fr_AG fr_AI fr_BE fr_BB \
             fr_BS fr_CA fr_CG fr_CH fr_CI fr_CM fr_CU fr_DO fr_DM fr_GD fr_GY fr_HT \
             fr_JM fr_KN fr_LC fr_LU fr_MA fr_MC fr_ML fr_MQ fr_PR fr_SN fr_SR fr_SX \
             fr_TT fr_VC fr_VI fy_DE it_CH ku_KU nl_AW nl_BE ro_RO ru_UA sq_AL sq_KV \
             sq_MK sr_ME sv_FI ta_IN ta_LK tr_CY vi_VN"

RPROVIDES:${PN}  = "${@" ".join(map(lambda s: "virtual-locale-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RPROVIDES:${PN} += "${@" ".join("virtual-locale-%s" % p.split('_')[0] for p in d.getVar('LANGUAGES').split())}"
RPROVIDES:${PN} += "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RCONFLICTS:${PN} = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RREPLACES:${PN}  = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RPROVIDES:${PN} += "${@" ".join(map(lambda s: "glibc-binary-localedata-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RCONFLICTS:${PN} = "${@" ".join(map(lambda s: "glibc-binary-localedata-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RREPLACES:${PN}  = "${@" ".join(map(lambda s: "glibc-binary-localedata-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"

do_install() {
	install -d ${D}${sysconfdir}/profile.d
	install -m 0644 ${UNPACKDIR}/locale.sh ${D}${sysconfdir}/profile.d/locale.sh
	install -d ${D}${LOCALEDIR2}
	install ${UNPACKDIR}/locale.alias ${D}${LOCALEDIR2}

	install -d ${D}${LOCALEDIR}
	cp --no-preserve=ownership --recursive ${S}/* ${D}/${LOCALEDIR}

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

	# Compatibility and fallback links for locales used by Enigma2 but not stored natively in the lite locale archive.
	link_locale() {
		target="$1"
		link="$2"
		fallback="${3:-en_US}"

		if [ -e ${D}${LOCALEDIR}/$link ] || [ -L ${D}${LOCALEDIR}/$link ]; then
			return 0
		fi

		if [ -e ${D}${LOCALEDIR}/$target ]; then
			ln -s $target ${D}${LOCALEDIR}/$link
		elif [ -n "$fallback" ] && [ -e ${D}${LOCALEDIR}/$fallback ]; then
			ln -s $fallback ${D}${LOCALEDIR}/$link
		else
			bbfatal "Unable to create locale fallback link $link: neither $target nor $fallback exists."
		fi
	}

	# en_GB
	link_locale en_GB en_EN
	link_locale en_GB en_AU
	link_locale en_GB en_AG
	link_locale en_GB en_BW
	link_locale en_GB en_BZ
	link_locale en_GB en_CA
	link_locale en_GB en_DK
	link_locale en_GB en_HK
	link_locale en_GB en_IE
	link_locale en_GB en_IL
	link_locale en_GB en_IN
	link_locale en_GB en_JM
	link_locale en_GB en_KH
	link_locale en_GB en_NG
	link_locale en_GB en_NZ
	link_locale en_GB en_PH
	link_locale en_GB en_SC
	link_locale en_GB en_SG
	link_locale en_GB en_TT
	link_locale en_GB en_ZA
	link_locale en_GB en_ZM
	link_locale en_GB en_ZW
	link_locale en_GB ta_IN
	link_locale en_GB ta_LK

	# nb_NO
	link_locale nb_NO no_NO
	link_locale nb_NO nn_NO

	# sr_RS
	link_locale sr_RS sr_YU
	link_locale sr_RS sq_AL
	link_locale sr_RS sq_KV
	link_locale sr_RS sq_MK
	link_locale sr_RS sr_ME

	# ar_AE
	link_locale ar_AE ar_EG
	link_locale ar_AE ar_BH
	link_locale ar_AE ar_DJ
	link_locale ar_AE ar_DZ
	link_locale ar_AE ar_EH
	link_locale ar_AE ar_ER
	link_locale ar_AE ar_IL
	link_locale ar_AE ar_IQ
	link_locale ar_AE ar_JO
	link_locale ar_AE ar_KM
	link_locale ar_AE ar_KW
	link_locale ar_AE ar_LB
	link_locale ar_AE ar_LY
	link_locale ar_AE ar_MA
	link_locale ar_AE ar_MR
	link_locale ar_AE ar_OM
	link_locale ar_AE ar_PS
	link_locale ar_AE ar_QA
	link_locale ar_AE ar_SA
	link_locale ar_AE ar_SD
	link_locale ar_AE ar_SO
	link_locale ar_AE ar_SS
	link_locale ar_AE ar_SY
	link_locale ar_AE ar_TD
	link_locale ar_AE ar_TN
	link_locale ar_AE ar_YE

	# ca_AD
	link_locale ca_AD ca_ES
	link_locale ca_AD ca_FR
	link_locale ca_AD ca_IT

	# de_DE
	link_locale de_DE de_AT
	link_locale de_DE de_BE
	link_locale de_DE de_CH
	link_locale de_DE de_IT
	link_locale de_DE de_LI
	link_locale de_DE de_LU

	# el_GR
	link_locale el_GR el_CY

	# es_ES
	link_locale es_ES es_AR
	link_locale es_ES es_BO
	link_locale es_ES es_CL
	link_locale es_ES es_CO
	link_locale es_ES es_CR
	link_locale es_ES es_CU
	link_locale es_ES es_DO
	link_locale es_ES es_EC
	link_locale es_ES es_GT
	link_locale es_ES es_HN
	link_locale es_ES es_MX
	link_locale es_ES es_NI
	link_locale es_ES es_PA
	link_locale es_ES es_PE
	link_locale es_ES es_PR
	link_locale es_ES es_PY
	link_locale es_ES es_SV
	link_locale es_ES es_US
	link_locale es_ES es_UY
	link_locale es_ES es_VE

	# fr_FR
	link_locale fr_FR fr_AG
	link_locale fr_FR fr_AI
	link_locale fr_FR fr_BE
	link_locale fr_FR fr_BB
	link_locale fr_FR fr_BS
	link_locale fr_FR fr_CA
	link_locale fr_FR fr_CG
	link_locale fr_FR fr_CH
	link_locale fr_FR fr_CI
	link_locale fr_FR fr_CM
	link_locale fr_FR fr_CU
	link_locale fr_FR fr_DO
	link_locale fr_FR fr_DM
	link_locale fr_FR fr_GD
	link_locale fr_FR fr_GY
	link_locale fr_FR fr_HT
	link_locale fr_FR fr_JM
	link_locale fr_FR fr_KN
	link_locale fr_FR fr_LC
	link_locale fr_FR fr_LU
	link_locale fr_FR fr_MA
	link_locale fr_FR fr_MC
	link_locale fr_FR fr_ML
	link_locale fr_FR fr_MQ
	link_locale fr_FR fr_PR
	link_locale fr_FR fr_SN
	link_locale fr_FR fr_SR
	link_locale fr_FR fr_SX
	link_locale fr_FR fr_TT
	link_locale fr_FR fr_VC
	link_locale fr_FR fr_VI

	# fy_NL
	link_locale fy_NL fy_DE

	# it_IT
	link_locale it_IT it_CH

	# tr_TR
	link_locale tr_TR ku_KU
	link_locale tr_TR tr_CY

	# nl_NL
	link_locale nl_NL nl_AW
	link_locale nl_NL nl_BE

	# ru_RU
	link_locale ru_RU ru_UA

	# sv_SE
	link_locale sv_SE sv_FI

	# en_US
	link_locale en_US vi_VN
}

FILES:${PN} = "${LOCALEDIR} ${LOCALEDIR2} ${sysconfdir}/profile.d"

do_package_qa[noexec] = "1"
