# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dags"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'TM-2T-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'TM-NANO-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'TM-NANO-2T' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'TM-NANO-3T Combo' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'TM-NANO 2 Super' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'TM-Single' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'TM-Twin-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'TM-NANO-SE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'TM-NANO-SE Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'TM-NANO-SE M2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'TM-NANO-SE M2 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'TM-NANO-SE Combo' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'IOS-100HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'IOS-200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'IOS-300HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'IOS-300HDV2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'Force 1' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'Force 1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'Force 1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'force2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'force2 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'force2 nano' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'force2 se' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'force2' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'force1' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'force1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs 200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt 200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'Optimuss OS1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'Optimuss OS2 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'Optimuss OS3 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'Optimuss OS1' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'Optimuss OS2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'Optimuss OS' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'MediaBox HD LX' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'FUSION HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'FUSION HD SE' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'PURE HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'FORCE2+ HV' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'FORCE3 UHD Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'FORCE3 UHD' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'REVO 4K' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'MEGASAT' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'MEGASAT' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'WorldVision' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'WorldVision' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'Jepssen' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'XSARIUS' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'XSARIUS' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'XSARIUS' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'XSARIUS' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'tm2t', 'update/tm2toe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'update/tmnanooe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'update/tmnano2t/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'update/tmnano2super/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'update/tmsingle/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'update/tmtwinoe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'update/tmnanose/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'update/tmnanoseplus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'update/tmnanosem2/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'update/tmnanoseplus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'update/tmnanosecombo/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'update/ios100/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'update/ios300/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'update/ios300new/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'update/force1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'update/force2solid/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'update/force2solid/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'update/force2plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'update/force2nano/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'update/force2se/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'update/force1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'update/optimussos1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'update/optimussos2plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'update/optimussos3plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'update/optimussos1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'update/optimussos2/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'update/optimussos/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'update/mediabox/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'update/fusionhd/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'update/fusionhdse/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'update/purehd/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'update/force2plushv/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'update/force3uhdplus' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'update/force3uhd' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'update/fusion4k' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'mmcblk0p1' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'mmcblk0p1' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'mmcblk0p1' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'mmcblk0p4' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'mmcblk0p4' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'mmcblk0p4' , '', d)}\
"

SPLASH1_BMP = "\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'splash1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'splash1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'splash1_power.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'splash1_power.bmp' , '', d)}\
"

MACHINE_DRIVER = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'dags7356tm' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'dags7356tm' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanose', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanoseplus', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnanosecombo', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'dags7335ci' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce1plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plus', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2nano', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2se', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'megaforce2', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'dags7335ci' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'dags7335ci' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'dags7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'dags7335ci' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'dags7335' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhd', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'fusionhdse', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'purehd', 'dags7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2plushv', 'dags73625' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhdplus', 'dags7252-300' , '', d)}\
${@base_contains('MACHINEBUILD', 'force3uhd', 'dags7252-200' , '', d)}\
${@base_contains('MACHINEBUILD', 'revo4k', 'dags7252-200' , '', d)}\
"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff textlcd ci skins1080 \
    ${@base_contains('MACHINEBUILD', 'fusionhd', 'fastscan dvb-c' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'fusionhdse', 'fastscan dvb-c' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'purehd', 'fastscan dvb-c' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'force2plushv', 'fastscan dvb-c' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'tmnanosem2plus', 'fastscan dvb-c' , '', d)} \
"

BOOTLOGO_FILENAME = "splash.bmp"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    dags-dvb-modules-${MACHINE_DRIVER} \
    dags-shutdown \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-dags --with-pcm --with-eac3 --with-amr --with-wmv \
    ${@base_contains('MACHINE', 'dags73625', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', 'dags7252', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
"

PREFERRED_PROVIDER_virtual/kernel = "linux-dags"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/transtreamproxy = "vuplus-transtreamproxy"

require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
