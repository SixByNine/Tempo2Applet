#!/usr/bin/python
# TCP server example
import socket,threading,sys,os
port=21001
psr_dir="/DATA/RUBICON_2/kei041/hitrun_archive/pulsars"

def main():
	server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	server_socket.bind(("", port))
	server_socket.listen(5)
	instanceid=0

	print "TCPServer Waiting for client on port %d"%port
	try:
		while 1:
			client_socket, address = server_socket.accept()
			print "I got a connection from ", address
			thread=SockThread()
			instanceid += 1
			thread.instanceid=instanceid
			thread.client_socket=client_socket
			thread.start()
				
	except KeyboardInterrupt:
		print "Closing Socket"
		server_socket.close()

class SockThread ( threading.Thread ):
	def run(self):
		par=""
		tim=""
		psr=""
		jmp=""
		finish=""
		start=""
		nofit=""
		sock=self.client_socket
		tmpdir="/tmp/webtempo2/%d"%self.instanceid
		os.system("rm -rf %s"%tmpdir)
		os.system("mkdir -p %s"%tmpdir)

		while 1:
			str=readsock(sock)
	#		print "\n'%s'\n"%str
			if str=="TEST":
				sock.send("OK##")
				break
			elif str=="NOFIT":
				nofit="-nofit"
				sock.send("OK##")
			elif str=="EXPECT_PAR":
				sock.send("OK##")
				par=readsock(sock)
				sock.send("OK##")
			elif str=="EXPECT_TIM":
				sock.send("OK##")
				tim=readsock(sock)
				sock.send("OK##")
			elif str=="EXPECT_JMP":
				sock.send("OK##")
				jmp=readsock(sock)
				sock.send("OK##")
			elif str=="EXPECT_PSR":
				sock.send("OK##")
				psr=readsock(sock)
				psr=psr.strip()
				cmd="cp %s/%s/*.tim %s"%(psr_dir,psr,tmpdir)
				print cmd
				os.system(cmd)
				sock.send("OK##")
			elif str=="EXPECT_SRT":
				sock.send("OK##")
				start=readsock(sock)
				sock.send("OK##")
			elif str=="EXPECT_FNS":
                                sock.send("OK##")
                                finish=readsock(sock)
                                sock.send("OK##")

			elif str=="FIT":
				file = open("%s/in.par"%tmpdir,"w")
				file.write(par)
				file.close()
				file = open("%s/in.tim"%tmpdir,"w")
                                file.write(tim)
                                file.close()
				elems=jmp.split("jj")
				jumpcmd=""
				for j in elems:
					if len(j) > 1:
						if j[0] == "-":
							jumpcmd=jumpcmd+"-jn %f "%float(j[1:])
						else:
							jumpcmd=jumpcmd+"-jp %f "%float(j)
				startcmd=finishcmd=""
				if len(start) > 0:
					startcmd = "-start %f"%float(start)
				if len(finish) > 0:
					finishcmd = "-finish %f"%float(finish)
				cmd="cd %s ; "%(tmpdir)+"""tempo2  -gr applet -outfile out.resid -s "{sat}\t{pre}\t{pre_phase}\t{post}\t{post_phase}\t{err}\t{binphase}\t{del}\t{freq}\t{flags}\n" -head """+"%s %s %s %s -f in.par in.tim >& out.tempo2"%(jumpcmd,startcmd,finishcmd,nofit)
				print cmd
				os.system(cmd)
				sock.send("OK##")
				try:
					file = open("%s/out.par"%tmpdir,"r")
					newpar=file.read()
					file.close()
				except:
					newpar="ERROR"
				try:
					file = open("%s/out.resid"%tmpdir,"r")
	                                resid=file.read()
	                                file.close()
				except:
					resid="ERROR"
				try:
	                                file = open("%s/out.tempo2"%tmpdir,"r")
	                                output=file.read()
	                                file.close()
				except:
					output="ERROR"


				sock.send("##PAR##\n")
				sock.send(newpar)
                                sock.send("##END##\n")
                                sock.send("##RES##\n")
                                sock.send(resid)
                                sock.send("##END##\n")
                                sock.send("##OUT##\n")
                                sock.send(output)
                                sock.send("##END##\n")

				break
			else:
				sock.send("ER##")
				break
		sock.close()
#		os.system("rm -rf %s"%tmpdir)

def readsock(client_socket):
	data=""
	while 1:
		data += client_socket.recv(512)
		#print data
		if data.endswith("##END##"):
			return data[:-7]




if __name__ == "__main__":
	main()
