export function formatDateTime(dateTimeString) {
	const date = new Date(dateTimeString);

	const monthNames = [
		"Jan", "Feb", "Mar", "Apr",
		"May", "Jun", "Jul", "Aug", 
		"Sep", "Oct", "Nov", "Dec"
	];

	const monthIndex = date.getMonth();
	const year = date.getFullYear();

	let hour = parseInt(date.getHours());
	let AmPm = 'AM';

	if (hour > 12) {
		hour = hour - 12;
		AmPm = "PM";
	}

	return monthNames[monthIndex] + ' ' + date.getDate() + ', ' + year + ' - ' + hour + ':' + date.getMinutes() + ' ' + AmPm;
}