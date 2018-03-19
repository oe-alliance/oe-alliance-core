require kernel-module-mali-utgard.inc

SRC_URI += "file://0001-hi3798mv200-support.patch"

MALI_PACKAGE_NAME = "DX910-SW-99002-${PV}"

SRC_URI[driver.md5sum] = "db3ef3258eb55700484ecadfdce1fee1"
SRC_URI[driver.sha256sum] = "496ba80684aa4236806891a8445978849f7dd07299f5e58b14d52cd5e7ba0536"

COMPATIBLE_MACHINE = "h9"
