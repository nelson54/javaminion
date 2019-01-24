
apt install nagios-nrpe-server nagios-plugins -y

pip install -r /opt/Custom-Nagios-Plugins/spring_boot_actuator/requirements.txt

sudo /etc/init.d/nagios-nrpe-server restart