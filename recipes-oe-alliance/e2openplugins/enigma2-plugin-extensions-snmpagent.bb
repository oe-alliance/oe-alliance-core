MODULE = "SnmpAgent"
DESCRIPTION = "SNMP Agent"

RDEPENDS_${PN} = "enigma2-plugin-extensions-bitrate twistedsnmp"

require openplugins-replace-pli.inc

require openplugins-distutils.inc

require assume-gplv2.inc
