# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dags"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'TM-2T-OE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'TM-NANO-OE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'TM-NANO-2T' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'TM-NANO-3T Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'TM-NANO 2 Super' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'TM-Single' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'TM-Twin-OE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'TM-Twin-4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'TM-NANO-SE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'TM-NANO-SE Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'TM-NANO-SE M2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'TM-NANO M3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'TM-NANO-SE M2 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'TM-NANO-SE Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'IOS-100HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'IOS-200HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'IOS-300HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'IOS-300HDV2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'Force 1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'Force 1 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'Force 1 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'force2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'force2 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'force2 nano' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'force2 se' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'force2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'force1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'force1 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs 200HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt 200HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'Optimuss OS1 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'Optimuss OS2 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'Optimuss OS3 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'Optimuss OS1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'Optimuss OS2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'Optimuss OS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'MediaBox HD LX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'FUSION HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'FUSION HD SE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'PURE HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'PURE HD SE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'FORCE2+ HV' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'FORCE3 UHD Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'FORCE3 UHD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'REVO 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'GALAXY 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'TM 4K super' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'Linux' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'LUNIX3 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'LUNIX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'FORCE 4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'LUNIX 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'LUNIX CO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'DUAL 4K UHD', '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'MEGASAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'MEGASAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'WorldVision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'WorldVision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'Jepssen' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'XSARIUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'Technomate' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'VALA' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'qviart' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'qviart' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'Iqon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'qviart' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'qviart' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'qviart', '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'update/tm2toe/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'update/tmnanooe/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'update/tmnano2t/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'update/force1plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'update/tmnano2super/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'update/tmsingle/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'update/tmtwinoe/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'update/tmtwin4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'update/tmnanose/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'update/tmnanoseplus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'update/tmnanosem2/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'update/tmnanom3/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'update/tmnanoseplus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'update/tmnanosecombo/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'update/ios100/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'update/ios200/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'update/ios300/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'update/ios300new/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'update/force1/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'update/force1plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'update/force1plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'update/force2solid/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'update/force2solid/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'update/force2plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'update/force2nano/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'update/force2se/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'update/force1/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'update/force1plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'update/ios200/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'update/ios200/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'update/optimussos1plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'update/optimussos2plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'update/optimussos3plus/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'update/optimussos1/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'update/optimussos2/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'update/optimussos/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'update/mediabox/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'update/fusionhd/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'update/fusionhdse/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'update/purehd/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'update/purehdse/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'update/force2plushv/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'update/force3uhdplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'update/force3uhd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'update/revo4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'update/galaxy4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'update/tm4ksuper' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'update/valalinux/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'update/lunix3-4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'update/lunix/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'update/force4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'update/lunix4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'update/lunixco/cfe' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'dual', '',d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'mtd6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'mmcblk0p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual','mmcblk0p12', '',d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'mmcblk0p13', '', d)}\
"

SPLASH1_BMP = "\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'splash1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'splash1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'splash1_os2.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'splash1_os2.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'splash1_os2.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'splash1_os1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'splash1_os1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'splash1_os1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'splash1_os2.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'splash1_os2.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'splash1_os1.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'splash1_power.bmp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'splash1_power.bmp' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'dags7356tm' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'dags7356tm' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'dags7252-200' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'dags7335ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'dags7356f1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'dags7356f1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'dags7335ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'dags7335ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'dags7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'dags7335ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'dags7335' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'dags73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'dags73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'dags7252-300' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'dags7252-200' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'dags7252-200' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'dags7252-300' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'dags7252-300' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'dags7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'dags7252-300' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'dags73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'dags72604' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'dags72604' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'dags73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'dagsmv200', '', d)}\
"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff ci skins1080 HDMI \
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'fan fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'fastscan dvb-c AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'fan fastscan dvb-c AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'fan fastscan dvb-c AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'fan fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'fastscan dvb-c RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'fastscan dvb-c RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'fastscan dvb-c RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force4', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'fastscan dvb-c' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'SCART RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'SCART' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'SCART RCA YUV' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'SCART RCA YUV' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'RCA YUV' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force1', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force2', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'AV_JACK' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'fastscan dvb-c' , '', d)} \
"

RCNAME = "\
${@bb.utils.contains('MACHINEBUILD', 'tm2t', 'tm1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano', 'tm2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2t', 'tm2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano3t', 'tm2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnano2super', 'tm2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmsingle', 'tm2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin', 'tm1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmtwin4k', 'tm6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanose', 'tm3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanoseplus', 'tm4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2', 'tm4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanom3', 'tm5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosem2plus', 'tm4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tmnanosecombo', 'tm3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios100hd', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios200hd', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hd', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'iqonios300hdv2', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1', 'iqon2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force1plus', 'iqon2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce1plus', 'megasat2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plus', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2nano', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2se', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'megaforce2', 'megasat1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1', 'iqon2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'worldvisionf1plus', 'iqon2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'roxxs200hd', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediaart200hd', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1plus', 'optimuss1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2plus', 'optimuss1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos3plus', 'optimuss2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos1', 'optimuss1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos2', 'optimuss1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'optimussos', 'optimuss1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox', 'mediabox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhd', 'fusionhd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'fusionhdse', 'fusionhd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehd', 'fusionhd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'purehdse', 'fusionhd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force2plushv', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhdplus', 'iqon3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force3uhd', 'iqon3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'revo4k', 'revo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxy4k', 'revo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tm4ksuper', 'tm6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'valalinux', 'vala' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix34k', 'qviart1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix', 'qviart1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'force4', 'iqon1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunix4k', 'qviart3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'lunixco', 'qviart4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dual', 'qviart5', '', d)}\
"

BOOTLOGO_FILENAME = "splash.bmp"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    dags-dvb-modules-${MACHINE_DRIVER} \
    ${@bb.utils.contains("MACHINE_FEATURES", "proxy", "platform-util-${MACHINE}", "", d)} \
    ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'hisi3798mv200-shutdown' , 'dags-shutdown', d)} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'v3d-libgles-${MACHINE}', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'swap', 'swapcreate-${MACHINE}', '',d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-pcm --with-eac3 --with-amr --with-wmv \
    ${@bb.utils.contains('MACHINE', 'dags73625', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'dags7252', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'dags72604', '--with-h265 --with-vb9' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'dagsmv200', '--with-h265 --with-vb8 --with-vb9 --with-spark --with-dts' , ' --with-dags', d)} \
"

PREFERRED_PROVIDER_virtual/kernel = "linux-dags"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs_dags72604 = "dags-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/transtreamproxy = "vuplus-transtreamproxy"

PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
