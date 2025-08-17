function confirmDelete(kind, idOrAccount) {
    if (confirm("Are you sure you want to delete this " + kind + "?")) {
        if (kind === 'customer') {
            window.location = "customer?action=delete&accountNo=" + encodeURIComponent(idOrAccount);
        } else if (kind === 'item') {
            window.location = "item?action=delete&id=" + encodeURIComponent(idOrAccount);
        } else if (kind === 'admin') {
            window.location = "admin?action=delete&id=" + encodeURIComponent(idOrAccount);
        }
    }
}
