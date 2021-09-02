console.log("This is Script File");
const toggleSidebar = () => {
	if ($(".sidebar").is(":visible")) {

		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	} else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
};

const search =() =>{
	let query = $("#search-result").val();
	
	if(query == "")
	{
		$("#search-result").hide();
	}
	else{
		console.log(query)
		$("#search-result").show();
	}
}