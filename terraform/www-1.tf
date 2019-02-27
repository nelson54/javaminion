resource "digitalocean_droplet" "www-1" {
    image = "ubuntu-18-04-x64"
    name = "www-1"
    region = "nyc3"
    size = "s-1vcpu-1gb"
    private_networking = false 
    ssh_keys = [
      "${var.ssh_fingerprint}"
    ]

    provisioner "remote-exec" {
        inline = ["echo \"Hello World\""]

    }
    
    provisioner "local-exec" {
        command = "ansible-playbook -i 68.183.157.229 ../playbooks/deploy.yml"
    }   
}
