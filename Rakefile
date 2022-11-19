require 'json'

namespace :tcs do

	task default: %w[deploy]

	desc "Authenticating with existing credentials"
	task :login do
		puts `docker login 2>&1`
	end


	desc "Cleaning Evironment Task"
	task :cleaning_environment_task do 
		puts "Cleaning Environment"
		puts `docker image prune -af`
		puts `docker volume prune -f 2>&1`
	end

	desc "Status Containers"
	task :status do 
		puts "Show Containers Status"
		puts `docker-compose ps 2>&1`
	end

	desc "Deploys Trust certification System Blockchain and launches all services and daemons needed to properly work"
	task :deploy => [
		:cleaning_environment_task,
		"ethereum:start",
		"ipfs:start",
		"platform:start",
		:status] do
	    puts "Deploying services..."
	end

	desc "UnDeploy Trust certification System Blockchain"
	task :undeploy => [ "ethereum:undeploy", "ipfs:undeploy", "platform:undeploy" ] do 
		puts "Undeploy Services"
		puts `docker-compose down -v 2>&1`
	end


	desc "Start Trust certification System Blockchain"
	task :start => [ "ethereum:start", "ipfs:start", "platform:start" ] do 
		puts "Start Trust certification System Blockchain"
	end 


	desc "Stop Containers"
	task :stop => [ "ethereum:stop", "ipfs:stop", "platform:stop" ] do
		puts "Stop Trust certification System Blockchain"
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


	

	## Deploy Platform
	namespace :platform do

		desc "Start Platform"
		task :start => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Start Platform Containers"
			puts `docker-compose -f ./platform/docker-compose.yml up -d`
		end 

		desc "Load initial ldap backup"
		task :init_ldap_backup => [ :check_docker_task ] do 
			puts "Load initial ldap backup"
			puts `docker exec -it openldap slapd-restore-data initial_backup.zip`
		end

		desc "Stop Platform"
		task :stop => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Stop Platform Containers"
			puts `docker-compose stop -f ./platform/docker-compose.yml 2>&1`
		end

		desc "UnDeploy Platform"
		task :undeploy => [:status] do 
			puts "Undeploy Services"
			puts `docker-compose down -f ./platform/docker-compose.yml -v 2>&1`
		end

		desc "Check Platform Deployment File"
		task :check_deployment_file do
			puts "Check Platform Deployment File ..."
		    raise "Deployment file not found, please check availability" unless File.file?("./platform/docker-compose.yml")
		    puts "Platform Deployment File OK!"
		end

		desc "Compile Project"
		task :compile do 
			puts "Starting build of all microservices in the project ..."
			if which('mvn')
				puts `mvn -f ./platform/microservices clean install`
				puts "Compilation finished! you could use ':package' task in order to generate the docker images"
			else
				raise "Please check that Apache Maven is visible and accessible in the PATH"
			end
			
		end 

		desc "Build Docker Images"
		task :package => [ :check_docker_task, :login, :compile ] do 
			puts "Starting the creation of the images for the microservices of the project"
			baseDirectory = "./platform/microservices"
			rakeCompileFile = "rake_compile.json"
			Dir.each_child('./platform/microservices') do |microservice|
				microserviceFolder = "#{baseDirectory}/#{microservice}"
				compileFile = "#{microserviceFolder}/#{rakeCompileFile}"
				puts "microservice folder: #{microserviceFolder}"
				if(File.file?(compileFile))
					compileFileContent = JSON.parse(File.read(compileFile))
					dockerImageName = "#{compileFileContent['account']}/#{compileFileContent['image']}:#{compileFileContent['version']}"
					puts "Build docker image: #{dockerImageName} at #{microserviceFolder}"  
					puts `docker build -t #{dockerImageName} #{microserviceFolder}`
					puts `docker images`
					puts "Docker image #{dockerImageName} has been created! trying to upload it!"  
					puts `docker push #{dockerImageName}`
		  		else
		  			puts "A rake compile file has not found in this microservice folder"
		  		end
			end
			puts "All microservices images have been built"
		end 

	end


	## Deploy IPFS Cluster
	namespace :ipfs do

		desc "Check IPFS Cluster Deployment File"
		task :check_cluster_deployment_file do
			puts "Check IPFS Cluster Deployment File ..."
		    raise "Deployment file not found, please check availability" unless File.file?("./ipfs_cluster/docker-compose.yml")
		    puts "Platform Deployment File OK!"
		end

		desc "Start IPFS Cluster"
		task :start => [ :check_docker_task, :login, :check_cluster_deployment_file ] do 
			puts "Start IPFS Cluster Containers"
			puts `docker-compose -f ./ipfs_cluster/docker-compose.yml up -d`
		end 

		desc "Stop IPFS Cluster"
		task :stop => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Stop Platform Containers"
			puts `docker-compose stop -f ./ipfs_cluster/docker-compose.yml 2>&1`
		end

		desc "UnDeploy IPFS Cluster"
		task :undeploy => [:status] do 
			puts "Undeploy Services"
			puts `docker-compose down -f ./ipfs_cluster/docker-compose.yml -v 2>&1`
		end

	end


	## Deploy Ethereum Network
	namespace :ethereum do

		desc "Check Private Ethereum Network Deployment File"
		task :check_network_deployment_file do
			puts "Check Private Ethereum Network Deployment File ..."
		    raise "Deployment file not found, please check availability" unless File.file?("./ethereum/docker-compose.yml")
		    puts "Private Ethereum Network Deployment File OK!"
		end

		desc "Start Private Ethereum Network"
		task :start => [ :check_docker_task, :login, :check_network_deployment_file ] do 
			puts "Start Private Ethereum Network Containers"
			puts `docker-compose -f ./ethereum/docker-compose.yml up -d`
		end 

		desc "Stop Private Ethereum Network"
		task :stop => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Stop Platform Containers"
			puts `docker-compose stop -f ./ethereum/docker-compose.yml 2>&1`
		end

		desc "UnDeploy Private Ethereum Network"
		task :undeploy => [:status] do 
			puts "Undeploy Services"
			puts `docker-compose down -f ./ethereum/docker-compose.yml -v 2>&1`
		end

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
	
end 


