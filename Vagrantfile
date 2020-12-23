Vagrant.configure("2") do |config|
``#vm box using
  config.vm.box = "hashicorp/bionic64"
	
	#syncing local folder to vm 
	config.vm.synced_folder "./", "/home/vagrant/workspace" 
	
	#shell script path to separate file provision.sh
	config.vm.provision "shell", path: "provision.sh"

end