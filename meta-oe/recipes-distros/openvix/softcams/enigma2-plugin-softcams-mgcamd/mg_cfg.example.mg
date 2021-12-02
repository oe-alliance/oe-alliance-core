# EMM messages
#    00 don't show any EMMs (default)
#    01 show only valid EMMs
#    02 show valid and bad EMMs with complete data display
M: { 01 }

# ECM messages
#    00 don't show anything about ecm
#    01 normal mode, show pids processed, decoded ecm and cw
#    02 verbose mode, show valid and bad ECMs with data
C: { 01 }

# AU
#    00 disable, no emm pids processed
#    01 enable, emm pids always processed for soft-au and shared cards
#    02 auto, emm started only if channel cant be decoded
#    03 process emm pids for network cards update only
A: { 02 }

# key update (sum 01 or 02 with 04)
#    01 update only new keys, default setting
#    02 update all keys (used for valid PMK checking)
#    04 enable TPS au
#    + tps SID, which pmt pid contains au pid
U: { 01 } 0x12c0

# config files folder (softcam, autoroll, ignore/priority)
#    00 files in /var/keys
#    01 files in /tmp
T: { 00 }

# network mode, use summ for several clients
#    00 no network (default)
#    01 newcamd netclient
#    02 radegast netclient
#    04 camd3 netclient
#    08 gbox netclient
G: { 01 }

# network retry, use summ for several options
#    00 disable
#    01 retry every new ecm
#    02 try to connect to offline shares every Q: seconds
#    04 try to detect and fast reconnect to lost (and not used atm) server
#    + XX messages number and YY seconds to reconnect
#    mg will reconnect to server, if no answer to last XX ecm/keepalive or
#    if no answer for last ecm or keepalive sent for YY seconds
#    set to 0 to disable
N: { 07 } 5 30

# network ecm timeout in seconds
K: { 05 }

# newcamd dead routes connect retry, sec
Q: { 600 }

# network shares priority
# 00 gbox, newcamd, radegast, camd3 (default)
# 01 camd3, radegast, newcamd, gbox
# 02 newcamd, camd3, gbox, radegast
P: { 00 }

# on screen display type
#    00 No OSD (default)
#    01 neutrino
#    02 enigma
#    03 relook
#    + user password for http auth
O: { 00 } username password

# on screen display Options, summ of:
#    01 show emu ecm 
#    02 show network shares messages
#    04 show decoding failed / fta
#    08 show emm keys update
#    + web port to use for osd
S: { 03 } 80

# Log option, summ of:
#    00 off
#    01 network udp log
#    02 log to console
#    04 file, appended ! delete it by yourself, before it eat all your hdd
#    + IP udp-port log-file-name
L: { 03 } 172.16.1.1 28007 /tmp/mgcamd.log

# keep ecm cache, seconds
# every cache entry takes 28 bytes, so 24h cache will take *only* 240-400kb of memory,
# for openbox/elanvision users with remote shares make sense to set it higher than default value, 
# to not hammer cards while timeshifting or playing crypted recordings.
E: { 15 }

# cache option, summ of:
#    00 Off (default)
#    01 Ecm pids cache, store pids used to decode in /tmp/ca_cache.list at exit, load at startup, same syntax as restore.list
#    02 Ecm data cache, remember CW for (E:) time 
#    04 Emm cache for network cards, do not resend the same emm twice, cache not cleared until restart
H: { 07 }

# reread files, summ of: 
#    00 No (default)
#    01 reread config file on channel change (including priority and ignore, but not replace/cache)
#    02 reread SoftCam.Key on channel change
#    04 reread SoftCam.Key if file changed
R: { 00 }

# debug, summ of
# 00 off (default)
# 01 debug ecm
# 02 debug emm
# 04 debug network ecm
# 08 debug network emm
# 16 debug network login
# 32 show mem/cpu stats every 1 min
# 64 add timestamp to log messages
D: { 00 }

# box type
# 00 autodetect, change only if you think wrong type detected.
# 01 dbox2
# 02 dreambox
# 03 triple-dragon
# 04 relook
# 05 openbox
B: { 00 }
