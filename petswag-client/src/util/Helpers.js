export function formatDateTime(dateTimeString) {
	const date = new Date(dateTimeString);

	const monthNames = [
		"Jan", "Feb", "Mar", "Apr",
    "May", "Jun", "Jul", "Aug", 
    "Sep", "Oct", "Nov", "Dec"
	];

	const monthIndex = date.getMonth();
	const year = date.getFullYear();

	return monthNames[monthIndex] + ' ' + date.getDate() + ' ' + year + ' - ' + date.getHours() + ':' + date.getMinutes();
}