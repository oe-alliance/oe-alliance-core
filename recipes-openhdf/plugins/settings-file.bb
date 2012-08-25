DESCRIPTION = "Enigma2 settings files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDF Team"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
VER ="1.0"
PR = "r3"

do_compile() {
}

do_install() {
	mkdir -p ${D}/etc/enigma2
	echo "config.CTVG.Cool_OK=Zap
config.CTVG.KeyEasy=false
config.CTVG.Cool_OKLong=Zap + Exit
config.CTVG.Cool_InfoLong=...
config.CTVG.Cool_Red=GuideSwitch
config.CTVG.KeyInfo=false
config.CTVG.KeyChannel=false
config.CTVG.CoolPiconON=false
config.CTVG.Cool_Yellow=PrimeTime
config.CTVG.KeySingle=false
config.CTVG.EasyNumber=false
config.CTVG.KeyCoolTV=false
config.CTVG.Cool_Info=...
config.EMC.moviecenter_sort=AZ
config.EMC.check_dvdstruct=false
config.EMC.cfgscan_suppress=true
config.EMC.movie_hide_del=true
config.EMC.timer_autocln=true
config.EMC.moviecenter_loadtext=false
config.EMC.remote_recordings=true
config.EMC.movie_show_format=true
config.EMC.movie_real_path=false
config.EMC.cfghide_enable=true
config.EMC.skin_able=true
config.EMC.movie_pathlimit=/media
config.EMC.movie_hide_mov=true
config.av.wss=false
config.av.colorformat=cvbs
config.av.policy_169=scale
config.av.policy_43=scale
config.av.videoport=DVI
config.av.videomode.DVI=1080i
config.misc.lastrotorposition=192
config.misc.firstrun=true
config.misc.initialchannelselection=false
config.misc.startCounter=1
config.misc.erase_speed=10
config.misc.languageselected=true
config.misc.flush_size=4194304
config.misc.epgcache_filename=/etc/enigma2/epg.dat
config.misc.defaultchosen=false
config.misc.videowizardenabled=true
config.radio.lastroot=1:7:1:0:0:0:0:0:0:0:FROM BOUQUET "bouquets.radio" ORDER BY bouquet;1:7:1:0:0:0:0:0:0:0:FROM BOUQUET "userbouquet.favourites.radio" ORDER BY bouquet;
config.radio.lastservice=1:0:2:3176:459:1:C00000:0:0:0:
config.skin.primary_skin=Nobile/extras/skin.xml
config.Nims.0.configMode=simple
config.osd.language=de_DE
config.tv.lastroot=1:7:1:0:0:0:0:0:0:0:FROM BOUQUET "bouquets.tv" ORDER BY bouquet;1:7:1:0:0:0:0:0:0:0:FROM BOUQUET "userbouquet.favourites.tv" ORDER BY bouquet;
config.tv.lastservice=1:0:19:71:D:85:C00000:0:0:0:
config.OpenWebif.webcache.collapsedmenus=remote|stream|config|control|info
config.recording.keep_timers=3
config.usage.movielist_trashcan_reserve=10
config.usage.movielist_trashcan_days=3
config.usage.frontend_priority=0
config.usage.hide_zap_errors=true
config.usage.hide_ci_messages=true
config.usage.show_second_infobar=0
config.hdmicec.control_tv_standby=true
config.hdmicec.enabled=true
config.hdmicec.control_receiver_standby=true
config.hdmicec.handle_tv_standby=true
config.hdmicec.control_tv_wakeup=true
config.hdmicec.handle_deepstandby_events=true
config.hdmicec.control_receiver_wakeup=true
config.hdmicec.handle_tv_wakeup=true
config.audio.volume=15
config.plugins.webradioFS.exitfrage=true
config.plugins.webradioFS.scroll_time=3
config.plugins.webradioFS.hauptmenu=true
config.plugins.autotimer.maxdaysinfuture=0
config.plugins.autotimer.interval=3
config.plugins.Webinterface.enabled=false
config.plugins.Webinterface.includemedia=true
config.plugins.Webinterface.https.enabled=false
config.plugins.Webinterface.autowritetimer=true
config.plugins.seriesplugin.pattern_title={org:s} S{season:02d}E{episode:02d} {title:s}
config.plugins.seriesplugin.pattern_description=S{season:02d}E{episode:02d} {title:s} {org:s}
config.plugins.pts.enabled=false
config.ParentalControl.configured=true
" >> ${D}/etc/enigma2/settings
        echo "[
	[
		[ " SeriesPlugin " ],
		[ " List of episode patterns in JSON notation " ],
		[ " String printf pattern                        , Setup entry " ]
	],
	[
		["Off"                                           , "Disabled"],
		
		["{org:s} S{season:02d}E{episode:02d}"           , "Org S01E01"],
		["{org:s} S{season:d}E{episode:d}"               , "Org S1E1"],

		["{org:s} S{season:02d}E{episode:02d} {title:s}" , "Org S01E01 Title"],
		["{org:s} S{season:d}E{episode:d} {title:s}"     , "Org S1E1 Title"],

		["{org:s} {title:s} S{season:02d}E{episode:02d}" , "Org Title S01E01"],
		["{org:s} {title:s} S{season:d}E{episode:d}"     , "Org Title S1E1"],

		["S{season:02d}E{episode:02d} {org:s}"           , "S01E01 Org"],
		["S{season:d}E{episode:d} {org:s}"               , "S1E1 Org"],

		["S{season:02d}E{episode:02d} {title:s} {org:s}" , "S01E01 Title Org"],
		["S{season:d}E{episode:d} {title:s} {org:s}"     , "S1E1 Title Org"],

		["{title:s} S{season:02d}E{episode:02d} {org:s}" , "Title S01E01 Org"],
		["{title:s} S{season:d}E{episode:d} {org:s}"     , "Title S1E1 Org"],

		["{title:s}"                                      , "Title"],
		["{title:s} {org:s}"                              , "Title Org"]
	]
]" >> ${D}/etc/enigma2/seriesplugin.cfg
}
