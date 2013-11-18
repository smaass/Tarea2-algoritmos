load E3M4J;
load E3M6J;
load E3M8J;

r(1,:) = E3M4J(:,2)./E3M4J(:,1);
r(2,:) = E3M6J(:,2)./E3M6J(:,1);
r(3,:) = E3M8J(:,2)./E3M8J(:,1);

for i = 1:3
    m(i) = mean(r(i,:));
    s(i) = std(r(i,:));
endfor

x = 4:2:8;

plot(x, m, '-o')
%errorbar(x, m, s)
%axis([2 8 1 2])
xlabel ("Total de trabajos");
ylabel ("makespan(online) / makespan(óptimo)");
%print -dsvg fig1.svg
