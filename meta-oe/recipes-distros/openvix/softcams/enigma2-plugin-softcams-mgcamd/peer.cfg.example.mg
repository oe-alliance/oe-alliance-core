# sample peer.cfg for gbox client in mgcamd
# syntax the same as in cwshare.cfg, 
# you can just copy cwshare.cfg to this file.
# see below for supported tags, others not supported (yet).

# max cards to send ecm too + preffered cards ids (up to 32)
X: { 05 } 1234 ABCD

# max card's distance to use for ecm
I: ( 05 }

# resend after 2 seconds, re-sync (send to all) after 3 seconds
N: { 0 0 0 0 2000 3000 }
# same, but for NDS cards
S: { 0 0 0 0 400 500 }

# my host and password
M: { my.dyndns.com { 1234ABCD }}

# peers, unlimited number, but only up to 32 unique ports to listen
# host, port to listen, remote port, password, levels (ignored)
D: { peer1.dyndns.com { 2471 3471 { ABCD1234 { 9 9 }}}}
D: { peer2.dyndns.com { 2471 3471 { 6789ABCD { 9 9 }}}}

# ignore ids
Y: { 01 02 }
Y: { 02 03 }

# always save ids
G: { 03 04 }
G: { 04 05 }

# virtual cards
V: { 01 { 05 00 7C 00 }}
V: { 02 { 18 01 04 01 }}
V: { 02 { 18 01 05 01 }}
V: { 03 { 18 01 40 01 }}
V: { 03 { 18 01 41 01 }}
