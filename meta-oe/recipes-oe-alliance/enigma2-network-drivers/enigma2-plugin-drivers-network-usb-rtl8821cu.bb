SUMMARY = "WiFi devices for Realtek 88x1CU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

# These packages have different Bluetooth recommendations from the allarch package.
PACKAGE_ARCH:inihdp = "${MACHINE_ARCH}"
PACKAGE_ARCH:sf8008 = "${MACHINEBUILD}"

RRECOMMENDS:${PN} = " \
        rt8821cu \
		linux-firmware-8821cu \
		${@'' if d.getVar('MACHINE') == 'inihdp' or d.getVar('MACHINEBUILD') == 'sf8008' else 'rtk-btusb'} \
"

PV = "1.0"
PR = "r4"

ALLOW_EMPTY:${PN} = "1"
