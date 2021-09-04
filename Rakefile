task default: %w[deploy]


desc "Deploys Trust certification System Blockchain and launches all services and daemons needed to properly work"
task :deploy => [
	:cleaning_environment_task,
	:start_ethereum_network,
	:start_platform,
	:status] do
    puts "Deploying services..."
end


desc "Start Private Ethereum Network"
task :start_ethereum_network => [ :check_docker_task, :login, :check_ethereum_network_deployment_file_task ] do 
	puts "Start Private Ethereum Network Containers"
	puts `docker-compose -f ./ethereum/docker-compose.yml up -d`
end 

desc "Check Private Ethereum Network Deployment File"
task :check_ethereum_network_deployment_file_task do
	puts "Check Private Ethereum Network Deployment File ..."
    raise "Deployment file not found, please check availability" unless File.file?("./ethereum/docker-compose.yml")
    puts "Private Ethereum Network Deployment File"
end


desc "Start Platform"
task :start_platform => [ :check_docker_task, :login, :check_platform_deployment_file_task ] do 
	puts "Start Platform Containers"
	puts `docker-compose -f ./platform/docker-compose.yml up -d`
end 

desc "Check Platform Deployment File"
task :check_platform_deployment_file_task do
	puts "Check Platform Deployment File ..."
    raise "Deployment file not found, please check availability" unless File.file?("./platform/docker-compose.yml")
    puts "Platform Deployment File OK"
end

desc "UnDeploy Trust certification System Blockchain"
task :undeploy => [:status] do 
	puts "Undeploy Services"
	puts `docker-compose down -v 2>&1`
end


desc "Status Containers"
task :status do 
	puts "Show Containers Status"
	puts `docker-compose ps 2>&1`
end


desc "Stop Containers"
task :stop => [ :check_docker_task ] do
	puts "Stop Containers"
	puts `docker-compose stop 2>&1`
	puts `docker-compose rm -f 2>&1`
end

desc "Cleaning Evironment Task"
task :cleaning_environment_task do 
	puts "Cleaning Environment"
	puts `docker image prune -af`
	puts `docker volume prune -f 2>&1`
end


desc "Check Docker and Docker Compose Task"
task :check_docker_task do
	puts "Check Docker and Docker Compose ..."
	if which('docker') && which('docker-compose')
		show_docker_version
		show_docker_compose_version
	else
		raise "Please check that Docker and Docker Compose are visible and accessible in the PATH"
	end
end


desc "Authenticating with existing credentials"
task :login do
	puts `docker login 2>&1`
end


## Utils Functions

def show_docker_version
  puts `docker version 2>&1`
end

def show_docker_compose_version
  puts `docker-compose version 2>&1`
end

# Cross-platform way of finding an executable in the $PATH.
# which('ruby') #=> /usr/bin/ruby
def which(cmd)
  exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']
  ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|
    exts.each { |ext|
      exe = File.join(path, "#{cmd}#{ext}")
      return exe if File.executable?(exe) && !File.directory?(exe)
    }
  end
  return nil
end